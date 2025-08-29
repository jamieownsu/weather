package com.chalupin.weather.presentation.home.state

sealed class ModalState {
    object Hidden : ModalState()
    data class ShowDeleteConfirmation(val cardDataState: CardDataState) : ModalState()
}