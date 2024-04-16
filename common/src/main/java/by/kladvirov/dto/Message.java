package by.kladvirov.dto;

import java.io.Serializable;

public interface Message extends Serializable {

    String getTemplate();

    String getEmail();

    String getSubject();

}