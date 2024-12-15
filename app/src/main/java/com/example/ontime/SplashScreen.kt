    package com.example.ontime

    import android.content.Intent
    import android.os.Bundle
    import android.os.Handler
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import com.example.ontime.login.SignUpActivity
    import com.google.firebase.auth.FirebaseAuth

    class SplashScreen : AppCompatActivity() {
        private lateinit var firebaseAuth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_splash_screen)

            firebaseAuth = FirebaseAuth.getInstance()

            val splashEnd: Long = 1200

            Handler().postDelayed({
                startActivity(Intent(this, SignUpActivity::class.java))
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }, splashEnd)

        }
        override fun onStart() {
            super.onStart()

            firebaseAuth.currentUser?.let {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }