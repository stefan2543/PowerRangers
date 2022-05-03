package powerrangers.movietracker.watchnext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.movietracker.watchnext.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        this.supportActionBar?.title = "Log in"

    }
}

