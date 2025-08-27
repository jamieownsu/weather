package com.chalupin.weather.presentation.home.util

sealed class ModalState {
    object Hidden : ModalState()
    data class ShowDeleteConfirmation(val cardData: CardData) : ModalState()
}