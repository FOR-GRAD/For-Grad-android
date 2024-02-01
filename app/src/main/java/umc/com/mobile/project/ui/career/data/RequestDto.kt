package umc.com.mobile.project.ui.career.data

import java.time.LocalDate

class RequestDto (var title : String, var content : String, var prize : String, var category : String,
                  var startDate: LocalDate, var endDate: LocalDate, var studentId: Int, var volunteerHour: Int, var award: String, var certificationType: String){
}