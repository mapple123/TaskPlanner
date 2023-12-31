package de.studiojan.taskkiller.listener;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

public class TextChangeListenerButtonNewTask implements TextWatcher {

    private View component;

    public TextChangeListenerButtonNewTask(View component){
        this.component = component;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        if (component.getVisibility() == View.GONE && count > 0 && !text.toString().trim().isEmpty())   showButtonWithAnimation();
        else if (component.getVisibility() == View.VISIBLE && count == 0 && text.toString().trim().isEmpty()) hideButtonWithAnimation();
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    private void showButtonWithAnimation() {
        if (component.getVisibility() == View.GONE) {
            component.setVisibility(View.VISIBLE);
            component.setTranslationX(component.getWidth()+100);
            component.animate()
                    .translationX(0)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }
                    });
        }
    }

    private void hideButtonWithAnimation() {
        if (component.getVisibility() == View.VISIBLE) {
            component.animate()
                    .translationX(component.getWidth())
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            component.setVisibility(View.GONE);
                        }
                    });
        }
    }
}
