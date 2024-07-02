package com.cameriere.message.dtos;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @param tableNumber
 * @param totalPrice
 * @param products
 * @param status
 * @param note
 */
public record OrderMessageDTO(int tableNumber, BigDecimal totalPrice, List<Long> products, String status, String note) {
}
