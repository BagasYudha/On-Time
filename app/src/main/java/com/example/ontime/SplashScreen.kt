    package com.example.ontime

    import android.content.Intent
    import android.os.Bundle
    import android.os.Handler
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity

    class SplashScreen : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_splash_screen)

            val splashEnd: Long = 1200

            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }, splashEnd)

        }
    }