package com.example.zavrsni

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Iznajmljivacki {
    var idApp: Int = 0
    var nazivApp: String = ""
}

class Rezervacije {
    var idRez: Int = 0
    var imeRez: String = ""
    var rezAppNaziv: String = ""
    //var rezAppID: Int = 0
    var datumDOL: String = ""
    var datumODL: String = ""
}