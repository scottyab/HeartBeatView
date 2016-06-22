HeartBeatView
=============

Simple custom view of a beating heart using scaling animation.

<img width="300" src="./docs/sample_screen_recording.gif" />


##How to use

It's just like the regular ImageView. (actually this custom view extends AppCompatImageView to make use of the tinting)

```xml
 <com.scottyab.HeartBeatView
                android:id="@+id/heartbeat"
                android:layout_width="72dp"
                android:layout_height="72dp"
                />
```                

In code you can `start()`, `stop()` the beating or `toggle()` to switch state. If you need to check is the heartbeatview is beating `isHeartBeating()`

###Customise

There are xml and code versions 

* Scale factor is the amount of size increase/decrease of the heart icon
* Duration is the time in milliseconds for a complete beat (decrease and increase)
* Duration for BPM - calculates and sets the duration based on the BPM (beats per minute)
* Tint (same as on ImageView)



##Add as dependancy

This library is not yet released in Maven Central, until then you can add as a library module or use JitPack.io

add remote maven url

    repositories {
        maven {
            url "https://jitpack.io"
        }
    }
then add a library dependency

    dependencies {
        compile 'com.github.scottyab:HeartBeatView:0.0.1'
    }
