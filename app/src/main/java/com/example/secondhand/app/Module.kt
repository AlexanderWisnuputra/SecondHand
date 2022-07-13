package com.example.secondhand.app

import com.example.secondhand.history.HistoryVM
import com.example.secondhand.notification.NotificationVM
import com.example.secondhand.sellerProduct.SPViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ProductVM = module {
    viewModel {
        SPViewModel()
    }
}

val NotifVM = module {
    viewModel {
        NotificationVM()
    }
}
val HistoVM = module {
    viewModel {
        HistoryVM()
    }
}
