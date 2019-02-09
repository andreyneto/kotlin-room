package br.com.andreyneto.room.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.andreyneto.room.R
import br.com.andreyneto.room.entities.Word
import br.com.andreyneto.room.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    companion object {
        const val NEW_WORD_ACTIVITY_REQUEST_CODE = 1
    }

    lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        wordViewModel.allWords.observe(this, Observer {
            adapter.words = it
        })

        fab.setOnClickListener { view ->
            val i = Intent(this, NewWordActivity::class.java)
            startActivityForResult(i, NEW_WORD_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE &&
            resultCode == RESULT_OK) {
            data?.let {
                val word =
                    Word(it.getStringExtra(NewWordActivity.EXTRA_REPLY))
                wordViewModel.insert(word)
            }
        } else {
            toast(R.string.empty_not_saved)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_clear -> {
                wordViewModel.deleteAll()
                toast(R.string.cleared)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
