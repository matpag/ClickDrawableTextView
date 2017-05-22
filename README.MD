#### This README it's under development, i need few days more to complete everything

## ClickDrawable[TextView / EditText / AutoCompleteTextView]

This library add the ability to listen for drawable click events for
TextView, EditText and AutoCompleteTextView normally added in XML
with ```android:drawableStart``` etc... or in code with
```setCompoundDrawables(...)``` and similar.

## Demo GIF

![Library showcase](https://media.giphy.com/media/xUA7aXqmppLR7a0JfG/giphy.gif)

## Installing

I'm waiting for jcenter support team to approve the library

To let the library work correctly, the first thing you need to call is
```CsDrawableSettings.init(Context context, String packageName)```

I suggest you to init the library at the end of ```onCreate()``` method of your
 custom ```Application``` class,
If you dont have a custom ```Application``` class, you can create one like this:

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
on where you create the ```AppApplication``` class) to the manifest.

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


## Docs

The library provides 3 views with extended functionalities for drawables.

Those are:

```ClickDrawableTextView```

```ClickDrawableEditText```

```ClickDrawableAutoCompleteTextView```

Each of them it's extending the ```AppCompat``` counterpart for max
compatibility with every SDK version.

The custom drawables are ```CsDrawable```s objects.

You can add a ```CsDrawable``` via XML or via Code,
below you can find both the implementations

#### XML
After declaring
```
xmlns:app="http://schemas.android.com/apk/res-auto"
```
in the top parent layout, you can specify the custom properties shown below

```
<com.matpag.clickdrawabletextview.ClickDrawableEditText
        android:id="@+id/click_drawable_edit_text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="10dp"
        android:drawablePadding="10dp"
        app:csStartDrawable="@drawable/close_blue"
        app:csStartDrawableHeight="50dp"
        app:csStartDrawableWidth="80dp"
        app:csEndDrawable="@drawable/close_blue"
        app:csEndDrawableHeight="50dp"
        app:csEndDrawableWidth="50dp"
        app:csTopDrawable="@drawable/close_blue"
        app:csTopDrawableHeight="40dp"
        app:csTopDrawableWidth="20dp"
        app:csBottomDrawable="@drawable/close_blue"
        app:csBottomDrawableHeight="30dp"
        app:csBottomDrawableWidth="30dp"
        />
```

To add a ```CsDrawable``` you can use:
```
app:csStartDrawable
app:csEndDrawable
app:csTopDrawable
app:csBottomDrawable
```
providing a reference to a drawable of any type.

If you don't specify any size for the drawable, automatically the drawable will be sized
to the width and height of the drawable itself (called **intrinsic** in the android
platform).

If you dont want to resize your original drawable resource, i suggest you to
add the width and the height you need for the drawable/s you added using respectively:
```
app:csStartDrawableHeight
app:csStartDrawableWidth
```
for the **Start** drawable
```
app:csEndDrawableHeight
app:csEndDrawableWidth
```
for the **End** drawable
```
app:csTopDrawableHeight
app:csTopDrawableWidth
```
for the **Top** drawable
```
app:csBottomDrawableHeight
app:csBottomDrawableWidth
```
for the **Bottom** drawable.

If you need to handle the initial visibility of the drawable you can use
```
app:csStartDrawableVisible
app:csEndDrawableVisible
app:csTopDrawableVisible
app:csBottomDrawableVisible
```
with ```true```(default) or ```false``` value


Don't forget to enable the RTL support in your ```AndroidManifest.xml``` adding
```android:supportsRtl="true"``` to the *application* tag


## Author

* **Mattia Pagini**
See also the list of [contributors](https://github.com/matpag/ClickDrawableTextView/contributors) who participated in this project.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details

