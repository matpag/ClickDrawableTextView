package com.matpag.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.matpag.clickdrawabletextview.ClickDrawableAutoCompleteTextView;
import com.matpag.clickdrawabletextview.ClickDrawableEditText;
import com.matpag.clickdrawabletextview.CsDrawable;

/**
 * Showcase activity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //####### ClickDrawableEditText - surrounded example ########
        //In this example we got the view from the XML, so go watch R.layout.activity_main to
        //understand more on how the properties were configured
        final ClickDrawableEditText mCdEditText = findViewById(R.id.click_drawable_edit_text);
        mCdEditText.setOnDrawableClickListener((view, position) -> {
            Toast.makeText(MainActivity.this, position.name(),
                    Toast.LENGTH_SHORT).show();
            //close the keyboard if opened
            mCdEditText.closeKeyboard();
        });

        //####### ClickDrawableEditText - search example ########
        //In this example we got the view from the XML, so go watch R.layout.activity_main to
        //understand more on how the properties were configured
        final ClickDrawableEditText mCdSearchEditText =
                findViewById(R.id.click_drawable_search_edit_text);
        mCdSearchEditText.setOnDrawableClickListener((view, position) -> {
            Toast.makeText(MainActivity.this, "Search started...",
                    Toast.LENGTH_SHORT).show();
            //close the keyboard if opened
            mCdEditText.closeKeyboard();
        });


        //######## ClickDrawableAutoCompleteTextView example ############
        final ClickDrawableAutoCompleteTextView mCdAutoComplete =
                findViewById(R.id.click_drawable_auto_text_view);

        //build a CsDrawable object with a PGN drawable
        CsDrawable csDrawable1 = new CsDrawable.Builder(this, R.drawable.ic_close_black_24dp)
                .setDrawableDpSize(30, 30)
                .setVisibility(false)
                .setTint(R.color.close_red)
                .build();

        //add to the END
        mCdAutoComplete.addEndCsDrawable(csDrawable1);

        //add a list of random strings to the autocompletetextview
        String[] strings = new String[]{"Ant", "Dog", "Cat", "Mouse", "Bird", "Bee",
                "Cow", "Parrot", "Snake", "Tiger"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, strings);
        mCdAutoComplete.setAdapter(adapter);

        //se on item click, when the user choose an element from the list, show the close drawable
        //and disable the focus on it preventing the user to edit the value again
        mCdAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            //show the cancel drawable
            mCdAutoComplete.showEndCsDrawable(true);
            //prevent user to change the value without prior clicking on the cancel drawable
            mCdAutoComplete.disableFocusOnText(true, true);
        });

        //set on CsDrawable click listener, when the user press the X drawable, we restore the
        //focus on the view and remove the right drawable
        mCdAutoComplete.setOnDrawableClickListener((view, position) -> {
            switch (position){
                case END: //if we touched the END drawable
                    //hide the close drawable
                    mCdAutoComplete.showEndCsDrawable(false);
                    //get focus on input (opening the keyboard)
                    mCdAutoComplete.enableFocusOnText(true);
                    //reset text
                    mCdAutoComplete.setText("");
                    break;
            }
        });
    }
}
