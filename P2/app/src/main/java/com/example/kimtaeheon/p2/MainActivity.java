package com.example.kimtaeheon.p2;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    BottomSheetBehavior<View> persistentBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.main_coordinator);

        initPeristentBottomSheet();
    }

    private void initPeristentBottomSheet(){
        View bottomSheet = coordinatorLayout.findViewById(R.id.main_bottom_sheet);
        bottomSheet.bringToFront();
        persistentBottomSheet=BottomSheetBehavior.from(bottomSheet);
    }
}
