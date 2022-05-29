package ua.vadymmy.it.words.ui.activities

import androidx.appcompat.app.AppCompatActivity

class KitDetailsActivity : AppCompatActivity() {

    override fun onBackPressed() {
        finish()
    }

    companion object {
        const val KEY_BUNDLE_WORD_KIT = "word_kit"
    }
}