package by.kladvirov.service.impl;

import by.kladvirov.dto.UserDto;
import by.kladvirov.dto.core.ReservationDto;
import by.kladvirov.dto.core.ServiceDto;
import by.kladvirov.dto.core.ServiceProviderDto;
import by.kladvirov.dto.payment.PaymentDto;
import by.kladvirov.dto.payment.json.Info;
import by.kladvirov.dto.payment.json.Provider;
import by.kladvirov.entity.Payment;
import by.kladvirov.enums.PaymentStatus;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.PaymentMapper;
import by.kladvirov.repository.PaymentRepository;
import by.kladvirov.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final WebClient webClient;

    private final PaymentMapper paymentMapper;

    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    @Override
    public PaymentDto findById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ServiceException("There is no such payment", HttpStatus.NOT_FOUND));
        return paymentMapper.toDto(payment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PaymentDto> findAll(Pageable pageable) {
        return paymentMapper.toDto(paymentRepository.findAll(pageable).toList());
    }

    @Transactional
    @Override
    public PaymentDto save(String header, Long reservationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        UserDto userDto = getUserDto(header, login);
        ReservationDto reservationDto = getReservationDto(header, login, reservationId);

        ServiceDto serviceDto = getServiceDto(header, reservationDto);
        ServiceProviderDto serviceProviderDto = getServiceProviderDto(header, serviceDto);

        Payment payment = buildPayment(buildInfo(buildService(buildProvider(serviceProviderDto), serviceDto), reservationDto), userDto, reservationDto);

        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void pay(Long id, Long reservationId, String header) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        UserDto userDto = getUserDto(header, login);
        ReservationDto reservationDto = getReservationDto(header, login, reservationId);

        PaymentDto paymentDto = findById(id);
        Payment payment = paymentMapper.toEntity(paymentDto);

        ServiceDto serviceDto = getServiceDto(header, reservationDto);

        if (payment.getExpiresAt().isBefore(ZonedDateTime.now())) {
            throw new ServiceException("Payment has expired");
        }

        BigDecimal totalCost = calculateTotalCost(reservationDto, serviceDto);
        if (userDto.getBalance().compareTo(totalCost) < 0) {
            throw new ServiceException("You don't have enough money to pay");
        }

        // заапдейтить баланс юзера
        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);
    }


    private UserDto getUserDto(String header, String login) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8080)
                        .path("/users/get-user")
                        .queryParam("login", login)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, header)
                .retrieve()
                .bodyToMono(UserDto.class)
                .onErrorResume(e -> Mono.error(new Exception("Error during getting user's dto", e)))
                .block();
    }

    private ReservationDto getReservationDto(String header, String login, Long reservationId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8083)
                        .path("/reservations/find-by-login-and-id")
                        .queryParam("login", login)
                        .queryParam("id", reservationId)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, header)
                .retrieve()
                .bodyToMono(ReservationDto.class)
                .onErrorResume(e -> Mono.error(new Exception("Error during getting reservation dto", e)))
                .block();
    }

    private ServiceDto getServiceDto(String header, ReservationDto reservationDto) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8083)
                        .path("/services/{id}")
                        .build(reservationDto.getServiceId()))
                .header(HttpHeaders.AUTHORIZATION, header)
                .retrieve()
                .bodyToMono(ServiceDto.class)
                .onErrorResume(e -> Mono.error(new Exception("Error during getting service dto", e)))
                .block();
    }

    private ServiceProviderDto getServiceProviderDto(String header, ServiceDto serviceDto) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8083)
                        .path("/service-providers/{id}")
                        .build(serviceDto.getServiceProviderId()))
                .header(HttpHeaders.AUTHORIZATION, header)
                .retrieve()
                .bodyToMono(ServiceProviderDto.class)
                .onErrorResume(e -> Mono.error(new Exception("Error during getting service provider dto", e)))
                .block();
    }

    private Info buildInfo(by.kladvirov.dto.payment.json.Service service, ReservationDto reservationDto) {
        return Info.builder()
                .service(service)
                .hours(reservationDto.getDateTo().getHour() - reservationDto.getDateFrom().getHour())
                .build();
    }

    private by.kladvirov.dto.payment.json.Service buildService(Provider provider, ServiceDto serviceDto) {
        return by.kladvirov.dto.payment.json.Service.builder()
                .provider(provider)
                .pricePerHour(serviceDto.getPricePerHour())
                .build();
    }

    private Provider buildProvider(ServiceProviderDto serviceProviderDto) {
        return Provider.builder()
                .name(serviceProviderDto.getName())
                .location(serviceProviderDto.getLocation())
                .build();
    }

    private Payment buildPayment(Info info, UserDto userDto, ReservationDto reservationDto) {
        return Payment.builder()
                .info(info)
                .userId(userDto.getId())
                .reservationId(reservationDto.getId())
                .build();
    }

    private BigDecimal calculateTotalCost(ReservationDto reservationDto, ServiceDto serviceDto) {
        long hoursBooked = Duration.between(reservationDto.getDateFrom(), reservationDto.getDateTo()).toHours();
        BigDecimal pricePerHour = BigDecimal.valueOf(serviceDto.getPricePerHour());
        return pricePerHour.multiply(BigDecimal.valueOf(hoursBooked));
    }

}
