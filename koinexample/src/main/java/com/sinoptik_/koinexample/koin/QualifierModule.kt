package com.sinoptik_.koinexample.koin

import com.sinoptik_.koinexample.utils.Utils
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.dsl.module

// 1. Enum ОБЯЗАН реализовывать интерфейс Qualifier от Koin
enum class PaymentType : Qualifier {
    MIR,
    SBP;

    // Переопределяем свойство value, возвращая имя элемента enum в виде строки
    override val value: QualifierValue
        get() = this.name
}

// Общий интерфейс для реализаций
interface PaymentGateway:Utils {
    fun processPayment(amount: Double)
}

// Реализация №1
class MirPaymentGateway : PaymentGateway {
    private val text="Оплата через МИР"

    override fun processPayment(amount: Double) = println(text)

    override fun cryMyName(addedText: String) {
        super.cryMyName(text)
    }
}

// Реализация №2
class SbpPaymentGateway : PaymentGateway {
    override fun processPayment(amount: Double) = println("Оплата $amount через СБП")
}


val paymentModule = module {
    // Регистрируем MirPaymentGateway с квалификатором PaymentType.MIR
    single<PaymentGateway>(qualifier = PaymentType.MIR) {
        MirPaymentGateway()
    }

    // Регистрируем SbpPaymentGateway с квалификатором PaymentType.SBP
    single<PaymentGateway>(qualifier = PaymentType.SBP) {
        SbpPaymentGateway()
    }
}
