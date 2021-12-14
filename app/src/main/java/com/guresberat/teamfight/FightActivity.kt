package com.guresberat.teamfight

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class FightActivity : AppCompatActivity() {

    private lateinit var uiBlocker: ImageView
    private lateinit var rollButton: Button
    private lateinit var diceImage: ImageView
    private lateinit var enemy_img1: ImageView
    private lateinit var enemy_img1_shadow: ImageView
    private lateinit var enemy_img2_shadow: ImageView
    private lateinit var enemy_img3_shadow: ImageView
    private lateinit var enemy_img4_shadow: ImageView
    private lateinit var enemy_img5_shadow: ImageView
    private lateinit var enemy_img6_shadow: ImageView
    private lateinit var friendly_img1_shadow: ImageView
    private lateinit var friendly_img2_shadow: ImageView
    private lateinit var friendly_img3_shadow: ImageView
    private lateinit var friendly_img4_shadow: ImageView
    private lateinit var friendly_img5_shadow: ImageView
    private lateinit var friendly_img6_shadow: ImageView
    private lateinit var enemy_img2: ImageView
    private lateinit var enemy_img3: ImageView
    private lateinit var enemy_img4: ImageView
    private lateinit var enemy_img5: ImageView
    private lateinit var enemy_img6: ImageView
    private lateinit var friendly_img1: ImageView
    private lateinit var friendly_img2: ImageView
    private lateinit var friendly_img3: ImageView
    private lateinit var friendly_img4: ImageView
    private lateinit var friendly_img5: ImageView
    private lateinit var friendly_img6: ImageView

    private var mEnemySelectedImage: ImageView? = null
    private var mFriendlySelectedImage: ImageView? = null
    private var mFriendlySelectedPosition: Int = 0
    private var mEnemySelectedPosition: Int = 0
    private var teamEnemy: ArrayList<Mobs>? = null
    private var teamFriendly: ArrayList<Mobs>? = null
    private var teamFriendlyRemaining: Int = 6
    private var teamEnemyRemaining: Int = 6
    private var mRolledDice: Int = 0
    private var mWins: Int = 0
    private var result: String = "1"
    private var mUserName: String? = null
    private var enemyDice: Int = 0
    private var friendlyDice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fight)
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        Toast.makeText(
            this,
            "After selecting mobs please click roll to roll a dice.The first roll is yours.Good Luck!",
            Toast.LENGTH_LONG
        ).show()
        handleTeams()
        rollDice()
        setDiceImage()
        mEnemySelectedImage = enemy_img1_shadow
        mFriendlySelectedImage = friendly_img1_shadow
        mEnemySelectedImage!!.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
        )
        mFriendlySelectedImage!!.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
        )
        enemy_img1_shadow.setOnClickListener {
            mobClickedEnemy(it)
            mEnemySelectedPosition = 0
        }
        enemy_img2_shadow.setOnClickListener {
            mobClickedEnemy(it)
            mEnemySelectedPosition = 1
        }
        enemy_img3_shadow.setOnClickListener {
            mobClickedEnemy(it)
            mEnemySelectedPosition = 2
        }
        enemy_img4_shadow.setOnClickListener {
            mobClickedEnemy(it)
            mEnemySelectedPosition = 3
        }
        enemy_img5_shadow.setOnClickListener {
            mobClickedEnemy(it)
            mEnemySelectedPosition = 4
        }
        enemy_img6_shadow.setOnClickListener {
            mobClickedEnemy(it)
            mEnemySelectedPosition = 5
        }
        friendly_img1_shadow.setOnClickListener {
            mobClickedFriendly(it)
            mFriendlySelectedPosition = 0
        }
        friendly_img2_shadow.setOnClickListener {
            mobClickedFriendly(it)
            mFriendlySelectedPosition = 1
        }
        friendly_img3_shadow.setOnClickListener {
            mobClickedFriendly(it)
            mFriendlySelectedPosition = 2
        }
        friendly_img4_shadow.setOnClickListener {
            mobClickedFriendly(it)
            mFriendlySelectedPosition = 3
        }
        friendly_img5_shadow.setOnClickListener {
            mobClickedFriendly(it)
            mFriendlySelectedPosition = 4
        }
        friendly_img6_shadow.setOnClickListener {
            mobClickedFriendly(it)
            mFriendlySelectedPosition = 5
        }

        rollButton.setOnClickListener {
            fight()
            uiBlocker.isVisible = true
        }
    }

    private fun rotateDice() {
        val hyperspaceJump: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        hyperspaceJump.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                setDiceImage()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
        diceImage.startAnimation(hyperspaceJump)
    }

    private fun handleTeams() {
        teamFriendly = ArrayList()
        teamEnemy = ArrayList()
        for (i in 1..6) {
            val a = getMobs()
            teamEnemy?.add(a)
        }
        for (i in 1..6) {
            val b = getMobs()
            teamFriendly?.add(b)
        }
        handleViews()
    }

    private fun rollDice() {
        mRolledDice = random(1, 5)
    }

    private fun fight() {
        if (teamFriendly!![mFriendlySelectedPosition].isAlive && teamEnemy!![mEnemySelectedPosition].isAlive) {
            rollButton.isClickable = false
            rollDice()
            rotateDice()
            friendlyDice = mRolledDice
            Handler(Looper.getMainLooper()).postDelayed({
                rollDice()
                rotateDice()
                enemyDice = mRolledDice
                if (friendlyDice > enemyDice) {
                    teamEnemy!![mEnemySelectedPosition].isAlive = false
                    handleDeceased(mEnemySelectedPosition, false)
                    teamEnemyRemaining -= 1
                    mWins += 1
                } else if (friendlyDice == enemyDice) {
                    Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()
                } else {
                    teamFriendly!![mFriendlySelectedPosition].isAlive = false
                    teamFriendlyRemaining -= 1
                    handleDeceased(mFriendlySelectedPosition, true)
                }
                if (teamEnemyRemaining == 0 || teamFriendlyRemaining == 0) {
                    if (teamFriendlyRemaining == 0) {
                        result = "0"
                    }
                    val score = 2 * teamFriendlyRemaining
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(Constants.RESULT, result)
                    intent.putExtra(Constants.USER_NAME, mUserName)
                    intent.putExtra(Constants.SCORE, score)
                    startActivity(intent)
                    finish()
                }
                rollButton.isClickable = true
                uiBlocker.isVisible = false
            }, 3000)
        } else {
            Toast.makeText(this, "Select Different Characters", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleDeceased(deceased: Int, isFriendly: Boolean) {
        if (isFriendly) {
            when (deceased) {
                0 -> {
                    friendly_img1_shadow.setImageResource(R.drawable.pallet_dead)
                    friendly_img1_shadow.isClickable = false
                }
                1 -> {
                    friendly_img2_shadow.setImageResource(R.drawable.pallet_dead)
                    friendly_img2_shadow.isClickable = false
                }
                2 -> {
                    friendly_img3_shadow.setImageResource(R.drawable.pallet_dead)
                    friendly_img3_shadow.isClickable = false
                }
                3 -> {
                    friendly_img4_shadow.setImageResource(R.drawable.pallet_dead)
                    friendly_img4_shadow.isClickable = false
                }
                4 -> {
                    friendly_img5_shadow.setImageResource(R.drawable.pallet_dead)
                    friendly_img5_shadow.isClickable = false
                }
                5 -> {
                    friendly_img6_shadow.setImageResource(R.drawable.pallet_dead)
                    friendly_img6_shadow.isClickable = false
                }
            }
        } else {
            when (deceased) {
                0 -> {
                    enemy_img1_shadow.setImageResource(R.drawable.pallet_dead)
                    enemy_img1_shadow.isClickable = false
                }
                1 -> {
                    enemy_img2_shadow.setImageResource(R.drawable.pallet_dead)
                    enemy_img2_shadow.isClickable = false
                }
                2 -> {
                    enemy_img3_shadow.setImageResource(R.drawable.pallet_dead)
                    enemy_img3_shadow.isClickable = false
                }
                3 -> {
                    enemy_img4_shadow.setImageResource(R.drawable.pallet_dead)
                    enemy_img4_shadow.isClickable = false
                }
                4 -> {
                    enemy_img5_shadow.setImageResource(R.drawable.pallet_dead)
                    enemy_img5_shadow.isClickable = false
                }
                5 -> {
                    enemy_img6_shadow.setImageResource(R.drawable.pallet_dead)
                    enemy_img6_shadow.isClickable = false
                }
            }
        }

    }

    private fun setDiceImage() {
        val drawableResource = when (mRolledDice) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_1
        }
        diceImage.setImageResource(drawableResource)
    }

    private fun random(first: Int, last: Int): Int {
        return (first..last).random()
    }

    private fun mobClickedEnemy(view: View) {
        if (view !== mEnemySelectedImage) {
            val image = view as ImageView

            image.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
            )
            if (teamEnemy!![mEnemySelectedPosition].isAlive) {
                mEnemySelectedImage!!.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.pallet_normal)
                )
            }
            mEnemySelectedImage = view
        }
    }

    private fun mobClickedFriendly(view: View) {
        if (view !== mFriendlySelectedImage) {
            val image = view as ImageView

            image.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
            )
            if (teamFriendly!![mFriendlySelectedPosition].isAlive) {
                mFriendlySelectedImage!!.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.pallet_normal)
                )
            }
            mFriendlySelectedImage = view
        }
    }

    private fun getMobs(): Mobs {
        val mob1 = Mobs(
            "Ghost",
            R.drawable.ghost,
            R.drawable.pallet_normal,
            true
        )
        val mob2 = Mobs(
            "Mummy",
            R.drawable.mummy,
            R.drawable.pallet_normal,
            true
        )
        val mob3 = Mobs(
            "Puppy",
            R.drawable.puppy,
            R.drawable.pallet_normal,
            true
        )
        val mob4 = Mobs(
            "Vampire",
            R.drawable.vampire,
            R.drawable.pallet_normal,
            true
        )
        val mob5 = Mobs(
            "Wizard",
            R.drawable.wizard,
            R.drawable.pallet_normal,
            true
        )
        val mob6 = Mobs(
            "Zombie",
            R.drawable.zombie,
            R.drawable.pallet_normal,
            true
        )
        return when ((1..6).random()) {
            1 -> mob1
            2 -> mob2
            3 -> mob3
            4 -> mob4
            5 -> mob5
            6 -> mob6
            else -> mob1
        }
    }

    private fun handleViews() {
        rollButton = findViewById(R.id.button)
        diceImage = findViewById(R.id.imageView)
        uiBlocker = findViewById(R.id.uiBlocker)
        uiBlocker.isClickable = false

        enemy_img1 = findViewById(R.id.enemy_img1)
        enemy_img2 = findViewById(R.id.enemy_img2)
        enemy_img3 = findViewById(R.id.enemy_img3)
        enemy_img4 = findViewById(R.id.enemy_img4)
        enemy_img5 = findViewById(R.id.enemy_img5)
        enemy_img6 = findViewById(R.id.enemy_img6)

        enemy_img1_shadow = findViewById(R.id.enemy_img1_shadow)
        enemy_img2_shadow = findViewById(R.id.enemy_img2_shadow)
        enemy_img3_shadow = findViewById(R.id.enemy_img3_shadow)
        enemy_img4_shadow = findViewById(R.id.enemy_img4_shadow)
        enemy_img5_shadow = findViewById(R.id.enemy_img5_shadow)
        enemy_img6_shadow = findViewById(R.id.enemy_img6_shadow)

        friendly_img1_shadow = findViewById(R.id.friendly_img1_shadow)
        friendly_img2_shadow = findViewById(R.id.friendly_img2_shadow)
        friendly_img3_shadow = findViewById(R.id.friendly_img3_shadow)
        friendly_img4_shadow = findViewById(R.id.friendly_img4_shadow)
        friendly_img5_shadow = findViewById(R.id.friendly_img5_shadow)
        friendly_img6_shadow = findViewById(R.id.friendly_img6_shadow)

        friendly_img1 = findViewById(R.id.friendly_img1)
        friendly_img2 = findViewById(R.id.friendly_img2)
        friendly_img3 = findViewById(R.id.friendly_img3)
        friendly_img4 = findViewById(R.id.friendly_img4)
        friendly_img5 = findViewById(R.id.friendly_img5)
        friendly_img6 = findViewById(R.id.friendly_img6)


        enemy_img1.setImageResource(teamEnemy!![0].image)
        enemy_img2.setImageResource(teamEnemy!![1].image)
        enemy_img3.setImageResource(teamEnemy!![2].image)
        enemy_img4.setImageResource(teamEnemy!![3].image)
        enemy_img5.setImageResource(teamEnemy!![4].image)
        enemy_img6.setImageResource(teamEnemy!![5].image)

        friendly_img1.setImageResource(teamFriendly!![0].image)
        friendly_img2.setImageResource(teamFriendly!![1].image)
        friendly_img3.setImageResource(teamFriendly!![2].image)
        friendly_img4.setImageResource(teamFriendly!![3].image)
        friendly_img5.setImageResource(teamFriendly!![4].image)
        friendly_img6.setImageResource(teamFriendly!![5].image)
    }
}