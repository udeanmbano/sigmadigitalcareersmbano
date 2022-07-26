// Generated by view binder compiler. Do not edit!
package com.sdb.retail.actionbutton.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.furkanakdemir.surroundcardview.SurroundCardView;
import com.sdb.retail.actionbutton.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class PinEntryBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText etxtFirstDigit;

  @NonNull
  public final EditText etxtLastDigit;

  @NonNull
  public final EditText etxtSecondDigit;

  @NonNull
  public final EditText etxtThirdDigit;

  @NonNull
  public final SurroundCardView otpFirstDigit;

  @NonNull
  public final SurroundCardView otpLastDigit;

  @NonNull
  public final SurroundCardView otpSecondDigit;

  @NonNull
  public final SurroundCardView otpThirdDigit;

  private PinEntryBinding(@NonNull ConstraintLayout rootView, @NonNull EditText etxtFirstDigit,
      @NonNull EditText etxtLastDigit, @NonNull EditText etxtSecondDigit,
      @NonNull EditText etxtThirdDigit, @NonNull SurroundCardView otpFirstDigit,
      @NonNull SurroundCardView otpLastDigit, @NonNull SurroundCardView otpSecondDigit,
      @NonNull SurroundCardView otpThirdDigit) {
    this.rootView = rootView;
    this.etxtFirstDigit = etxtFirstDigit;
    this.etxtLastDigit = etxtLastDigit;
    this.etxtSecondDigit = etxtSecondDigit;
    this.etxtThirdDigit = etxtThirdDigit;
    this.otpFirstDigit = otpFirstDigit;
    this.otpLastDigit = otpLastDigit;
    this.otpSecondDigit = otpSecondDigit;
    this.otpThirdDigit = otpThirdDigit;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static PinEntryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static PinEntryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.pin_entry, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static PinEntryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.etxtFirstDigit;
      EditText etxtFirstDigit = ViewBindings.findChildViewById(rootView, id);
      if (etxtFirstDigit == null) {
        break missingId;
      }

      id = R.id.etxtLastDigit;
      EditText etxtLastDigit = ViewBindings.findChildViewById(rootView, id);
      if (etxtLastDigit == null) {
        break missingId;
      }

      id = R.id.etxtSecondDigit;
      EditText etxtSecondDigit = ViewBindings.findChildViewById(rootView, id);
      if (etxtSecondDigit == null) {
        break missingId;
      }

      id = R.id.etxtThirdDigit;
      EditText etxtThirdDigit = ViewBindings.findChildViewById(rootView, id);
      if (etxtThirdDigit == null) {
        break missingId;
      }

      id = R.id.otpFirstDigit;
      SurroundCardView otpFirstDigit = ViewBindings.findChildViewById(rootView, id);
      if (otpFirstDigit == null) {
        break missingId;
      }

      id = R.id.otpLastDigit;
      SurroundCardView otpLastDigit = ViewBindings.findChildViewById(rootView, id);
      if (otpLastDigit == null) {
        break missingId;
      }

      id = R.id.otpSecondDigit;
      SurroundCardView otpSecondDigit = ViewBindings.findChildViewById(rootView, id);
      if (otpSecondDigit == null) {
        break missingId;
      }

      id = R.id.otpThirdDigit;
      SurroundCardView otpThirdDigit = ViewBindings.findChildViewById(rootView, id);
      if (otpThirdDigit == null) {
        break missingId;
      }

      return new PinEntryBinding((ConstraintLayout) rootView, etxtFirstDigit, etxtLastDigit,
          etxtSecondDigit, etxtThirdDigit, otpFirstDigit, otpLastDigit, otpSecondDigit,
          otpThirdDigit);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
