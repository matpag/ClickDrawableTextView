# ClickDrawable - TextView/EditText/AutoComplete

This library add the ability to listen for drawable click events for TextView, EditText and AutoCompleteTextView normally added in XML
with ```android:drawableStart``` etc... or in code with ```setCompoundDrawables(...)``` and similar.
Also with this library you can flat a layout used to side any widget with an ```ImageView``` or ```ImageButton``` added to listen for click on the icon, to one unique custom view.

For example converting this XML:
```
<LinearLayout 
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">
    <TextView
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_gravity="center_vertical"
            android:layout_height="wrap_content"
            android:text="this textview looks nice"/>
    <ImageView
            android:layout_width="30dp"
            android:layout_height="30dp"
            app:srcCompat="@drawable/my_icon
            android:text="this textview looks nice"/>
</LinearLayout> 
```
Into this:
```
<com.matpag.clickdrawabletextview.ClickDrawableTextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="this textview looks nice"
        app:csStartDrawable="@drawable/my_icon
        app:csStartDrawableHeight="30dp"
        app:csStartDrawableWidth="30dp"/>
```
Removing a ViewGroup, removing inefficient weights and adding a lot more functionalities.

## Demo
This GIF shows a ```ClickDrawableEditText``` with clickable drawables all around it (please never do this in a real app, it's really unpleasant :D) and a ```ClickDrawableAutoCompleteTextView``` with support for blocking inputs after user choice, until cancel icon is pressed.

![Library showcase](https://media.giphy.com/media/xUA7aXqmppLR7a0JfG/giphy.gif)

## Setup

This library is minSdk 17 because of [this](https://developer.android.com/reference/android/content/res/Configuration.html#SCREENLAYOUT_LAYOUTDIR_RTL) flag we need to know if the app is running in RTL mode or not.

### 1. Add dependency in build.gradle
```compile 'com.matpag:clickdrawabletextview:0.6.0'```

### 2. Use a support theme
Use one of the theme from ```Theme.AppCompat.*``` for your application theme.

### 3. Initialize library in custom ```Application```
Call ```CsDrawableSettings.init(Context context, String packageName)``` in the ```onCreate``` of your custom ```Application``` class. 
In this way:
```java
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Init the library
        CsDrawableSettings.init(this, BuildConfig.APPLICATION_ID);
    }
}
```
then you need to change your ```AndroidManifest.xml``` to load your custom
application class instead of the default one adding the
```android:name".AppApplication"``` (you should update the path accordingly based
on where you create the ```AppApplication``` class) to the manifest if you already didn't.

Something similar to this:
```
<application
        android:name=".AppApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:supportsRtl="true"
        ....>

        <!-- Other declarations -->

</application>
```

### 4. RTL support
If you want to support RTL, you simply need to add ```android:supportsRtl="true"``` to the *application* tag in your ```AndroidManifest.xml``` and the library will handle everything for you.

## Docs

The library provides 3 custom views with extended functionalities for drawables.

#### 1. ```ClickDrawableTextView```
#### 2. ```ClickDrawableEditText```
#### 3. ```ClickDrawableAutoCompleteTextView```

Each of them it's extending the ```AppCompat``` counterpart class for max compatibility with every supported SDK version

The custom drawables are ```CsDrawable```s objects.

You can add a ```CsDrawable``` via XML or via Code, below you can find both the implementations.

#### XML
After declaring
```
xmlns:app="http://schemas.android.com/apk/res-auto"
```
in the top parent layout, you can specify the custom properties shown below in any of the
supported views.

To add the drawable to the view in position you need, you can use:
```
app:csStartDrawable="@drawable/my_icon"
app:csEndDrawable="@drawable/my_icon"
app:csTopDrawable="@drawable/my_icon"
app:csBottomDrawable="@drawable/my_icon"
```
providing a reference to a drawable of any type (png, xml drawable, vector drawable)

If you don't specify any size for the drawable, automatically the drawable will be sized to the width and height of the drawable itself (called **intrinsic** in the android platform).

If you dont want to resize your original drawable resource (and you really shouldn't if not necessary), i suggest you to specify the width and the height in **dp** you need using:
```
app:csStartDrawableHeight="30dp"
app:csStartDrawableWidth="30dp"
```
for the **Start** drawable
```
app:csEndDrawableHeight="30dp"
app:csEndDrawableWidth="30dp"
```
for the **End** drawable
```
app:csTopDrawableHeight="30dp"
app:csTopDrawableWidth="30dp"
```
for the **Top** drawable
```
app:csBottomDrawableHeight="30dp"
app:csBottomDrawableWidth="30dp"
```
for the **Bottom** drawable.

If you need to handle the initial visibility of the drawable(s) you can use
```
app:csStartDrawableVisible="true | false"
app:csEndDrawableVisible"true | false"
app:csTopDrawableVisible"true | false"
app:csBottomDrawableVisible"true | false"
```

#### Code

Use this XML as example
```
<com.matpag.clickdrawabletextview.ClickDrawableEditText
        android:id="@+id/click_drawable_edit_text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
Get the reference in code as usual

```java
ClickDrawableEditText mCdEditText = (ClickDrawableEditText)findViewById(R.id.click_drawable_edit_text);
```

Create a ```CsDrawable``` object using the builder class

```java
CsDrawable csDrawable = new CsDrawable.Builder(this, R.drawable.ic_close_red_24dp)
                .setDrawableDpSize(30, 30) //or .setDrawablePixelSize(width, height) for pixel
                .setVisibility(false)
                .build();
```

Now you can add the ```CsDrawable``` to the view using one (or more) of the following methods
```java
mCdEditText.addStartCsDrawable(csDrawable);
mCdEditText.addTopCsDrawable(csDrawable);
mCdEditText.addEndCsDrawable(csDrawable);
mCdEditText.addBottomCsDrawable(csDrawable);
```

To change the visibility later, you can use
```java
mCdEditText.showStartCsDrawable(true | false);
mCdEditText.showTopCsDrawable(true | false);
mCdEditText.showEndCsDrawable(true | false);
mCdEditText.showBottomCsDrawable(true | false);
```

## Listen to Drawables click events
Now that you have configured your ```ClickDrawable*``` view. You can add a listener for the drawable clicked using 
```java
viewInstance.setOnDrawableClickListener(OnDrawableClickListener listener)
```.

Example:
```java
viewInstance.setOnDrawableClickListener(new OnDrawableClickListener() {
            @Override
            public void onClick(View view, DrawablePosition position) {
                Toast.makeText(context, "Touched " + positiong.name() + " drawable", Toast.LENGHT_SHORT).show();
            }
        });
```
If you added more than 1 drawable to the view, you can create a switch case to distinguish between them
```java
viewInstance.setOnDrawableClickListener(new OnDrawableClickListener() {
            @Override
            public void onClick(View view, DrawablePosition position) {
                switch (position){
                    case START: startDrawableClicked(); break;
                    case END: endDrawableClicked(); break;
            }
        });
```

### Advanced features
If you need to block the user after some action, like after selecting an item in the ```ClickDrawableAutoCompleteTextView``` dropdown.
You can block the view from receiving the focus for editing *(without disabling the view using ```setEnabled(false)```, because this prevent the dispatch of every events on all the surface view)* using 
```viewInstance.disableFocusOnText(boolean preventReFocus, boolean closeKeyboard)```.

```preventReFocus``` : Passing ```true```, will prevent re-focus on another EditText in the same ViewGroup (if present)
```closeKeyboard``` : Passing ```true```, will close the keyboard (if actually open)

Then if the user press the cancel icon, you should re-enable the editing on the view to let him pick another choice using 
```viewInstance.enableFocusOnText(boolean openKeyboard);```

```openKeyboard``` : Passing ```true```, will open the keyboard automatically (if actually closed)

## Author

* **[Mattia Pagini](https://github.com/matpag)** 

## Contributing

Please open issue for bug fixes or feature requests and send pull request after discussing in the issue (to prevent waste of your time on something that can't be merged)
If you find typos somewhere or a lack of documentation, feel free to create a pull request.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details

