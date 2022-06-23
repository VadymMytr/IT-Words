package ua.vadymmy.it.words.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.ui.adapters.recyclers.WordsAdapter
import ua.vadymmy.it.words.utils.startActivity

class LearningResultActivity : AppCompatActivity() {

    override fun onBackPressed() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_result)
        val kit = intent.getSerializableExtra(
            KitDetailsActivity.KEY_BUNDLE_WORD_KIT
        ) as LearningWordKit

        findViewById<RecyclerView>(R.id.learn_result_words_recycler).apply {
            adapter = WordsAdapter({}, { word, adapterPosition -> }).also {
                it.setWords(kit)
            }
        }

        val answersAmount = intent.getIntExtra(RESULT_ACT_BUNDLE_AMOUNT, 0)
        findViewById<CircularProgressBar>(R.id.learn_result_progress_bar).apply {
            progress = answersAmount.toFloat()
            progressMax = if (kit.size < 10) kit.size.toFloat() else 10f
        }

        findViewById<TextView>(R.id.learning_word_congrats).apply {
            text = getString(
                R.string.learning_result_progress_placeholder,
                answersAmount,
                if (kit.size < 10) kit.size else 10
            )
        }

        findViewById<Button>(R.id.learn_result_to_kit_button).setOnClickListener {
            startActivity(
                KitDetailsActivity::class.java,
                intentData = bundleOf(KitDetailsActivity.KEY_BUNDLE_WORD_KIT to kit)
            )

            finish()
        }
    }

    companion object {
        val RESULT_ACT_BUNDLE_AMOUNT = "bundle_amount"
    }
}