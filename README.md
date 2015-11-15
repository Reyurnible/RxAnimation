[![Kotlin](https://img.shields.io/badge/kotlin-1.0.0--beta--2422-blue.svg)](http://kotlinlang.org)
[![DUB](https://img.shields.io/dub/l/vibe-d.svg)](https://github.com/mplatvoet/kovenant/blob/master/LICENSE)
[![Kotlin Slack](https://img.shields.io/badge/chat-kotlin%20slack-orange.svg)](http://kotlinslackin.herokuapp.com)

# RxAnimation
This is Animation wrapping Observable.
You can get event Observable.
And you can call animation chain more simple.

## Download
Animation bindings:

```
compile 'com.github.reyurnible:rxanimation:1.0.0'
```

using Kotlin:

```
compile 'com.github.reyurnible:rxanimation-kotlin:1.0.0'
```

```
repositories {
    maven {
        url 'http://dl.bintray.com/reyurnible/maven'
    }
}
```

## Public Classes
- RxAnimation
- RxAnimator

## Example

### Simple Usage
in Java

```
TextView textView = (TextView) findViewById(R.id.textView);
Animation animation = new ScaleAnimation(0.0f, 0.0f, 1.0f, 1.0f);
RxAnimation.event(animation, textView)
    .subscribe(new Subscriber<AnimationEvent>() {
        @Override public void onCompleted() {
               
        }
    
        @Override public void onError(Throwable e) {
            
        }
    
        @Override public void onNext(AnimationEvent animationEvent) {
            switch (animationEvent.kind()) {
                case START:
                    break;
                case REPEAT:
                    break;
                case END:
                    break;
            }
        }
    });
```

in Kotlin

```
val view: TextView = findViewById(R.id.textView) as TextView
val animation: Animation = ScaleAnimation(0.0f, 0.0f, 1.0f, 1.0f)
animation.bindView(view).subscribe(object : Subscriber<AnimationEvent>() {
    override fun onCompleted() {
        
    }
    
    override fun onNext(t: AnimationEvent?) {
        when(t!!.kind()) {
            AnimationEvent.Kind.START -> {
                // START
            }
            AnimationEvent.Kind.END -> {
                // END
            }
            AnimationEvent.Kind.REPEAT -> {
                // REPEAT
            }
        }
    }
    
    override fun onError(e: Throwable?) {
        
    }
})
```

### Combine Usage
 
```
private fun startAnimations() {
    val alphaAnimation: AlphaAnimation = AlphaAnimation(0.0f, 1.0f)
    alphaAnimation.duration = 3000
    val alphaAnimationObserver = alphaAnimation.bindView(formLayout).map {
        Log.d(TAG, it.toString())
        it.kind() == AnimationEvent.Kind.END
    }

    val translationAnimator: ObjectAnimator = ObjectAnimator.ofFloat(formLayout, "translationY", -1000f, 0f);
    translationAnimator.setDuration(3000);
    val translateAnimatorObserver = translationAnimator.events().map {
        Log.d(TAG, it.toString())
        it.kind() == AnimatorEvent.Kind.END
    }

    Observable.combineLatest(translateAnimatorObserver, alphaAnimationObserver, { t1, t2 ->
        Log.d(TAG, "T1:" + t1.toString())
        Log.d(TAG, "T2:" + t2.toString())
        t1.and(t2)
    }).filter { it }.subscribe { validate ->
        // post event
        Log.d(TAG, "End All Animation")
    }
}
```

## Licences

```
Copyright (c) 2015 Shun Hosaka

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```

