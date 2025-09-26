package Memento.entities;

public enum AdStatus {
    PENDING,   // Criado mas ainda não aprovado
    APPROVED,  // Admin aprovou, mas ainda não começou
    ACTIVE,    // Está a ser exibido
    EXPIRED    // Já não aparece
}

