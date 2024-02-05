package com.golfpvcc.shoppinglist.ui.coursedetail

import android.util.Log
import com.golfpvcc.shoppinglist.ui.Utils
import com.golfpvcc.shoppinglist.ui.detail.HoleHandicap

fun currentHandicapConfiguration(cardHandicap: IntArray, availableHandicap: Array<HoleHandicap>) {

    for (idx in 0 until availableHandicap.size) {
        availableHandicap[idx].holeHandicap = idx + 1   // display handicap number in drop down menu
        availableHandicap[idx].available = true
    }
    cardHandicap.forEachIndexed { inx, holeHdcp ->
        if (holeHdcp != 0) {
            var removeHdcpFromPool: HoleHandicap? = availableHandicap.find { it.holeHandicap == holeHdcp }
            if (removeHdcpFromPool != null) {
                removeHdcpFromPool.available = false
            }
        }
    }
}

