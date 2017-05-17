#### This README it's under development, i need few days more to complete everything

## ClickDrawable[TextView / EditText / AutoCompleteTextView]

This library add the ability to listen for drawable click events for
TextView, EditText and AutoCompleteTextView normally added in XML
with ```android:drawableStart``` etc... or in code with
```setCompoundDrawables(...)``` and similar.

## Demo GIF

TODO add demo GIF (maybe hosted on my domain)


## Installing

TODO (waiting for jcenter approval)

## API Docs

The library provides 3 views with extended functionalities for drawables.

Those are:

```ClickDrawableTextView```

```ClickDrawableEditText```

```ClickDrawableAutoCompleteTextView```

Each of them it's extending the ```AppCompat``` counterpart for max
compatibility with every SDK version.

You can add a ```CsDrawable``` via XML or via Code, below i will show you both the implementations

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

