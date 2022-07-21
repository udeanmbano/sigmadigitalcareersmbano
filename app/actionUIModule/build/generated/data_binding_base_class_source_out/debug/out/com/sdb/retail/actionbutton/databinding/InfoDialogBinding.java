// Generated by view binder compiler. Do not edit!
package com.sdb.retail.actionbutton.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.airbnb.lottie.LottieAnimationView;
import com.sdb.retail.actionbutton.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class InfoDialogBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final LottieAnimationView animationView;

  @NonNull
  public final ImageView imgSuccess;

  @NonNull
  public final LinearLayout loadingContainer;

  @NonNull
  public final TextView txtvInfoMessage;

  @NonNull
  public final TextView txtvLoadingMessage;

  private InfoDialogBinding(@NonNull ConstraintLayout rootView,
      @NonNull LottieAnimationView animationView, @NonNull ImageView imgSuccess,
      @NonNull LinearLayout loadingContainer, @NonNull TextView txtvInfoMessage,
      @NonNull TextView txtvLoadingMessage) {
    this.rootView = rootView;
    this.animationView = animationView;
    this.imgSuccess = imgSuccess;
    this.loadingContainer = loadingContainer;
    this.txtvInfoMessage = txtvInfoMessage;
    this.txtvLoadingMessage = txtvLoadingMessage;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static InfoDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static InfoDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.info_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static InfoDialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.animation_view;
      LottieAnimationView animationView = ViewBindings.findChildViewById(rootView, id);
      if (animationView == null) {
        break missingId;
      }

      id = R.id.imgSuccess;
      ImageView imgSuccess = ViewBindings.findChildViewById(rootView, id);
      if (imgSuccess == null) {
        break missingId;
      }

      id = R.id.loadingContainer;
      LinearLayout loadingContainer = ViewBindings.findChildViewById(rootView, id);
      if (loadingContainer == null) {
        break missingId;
      }

      id = R.id.txtvInfoMessage;
      TextView txtvInfoMessage = ViewBindings.findChildViewById(rootView, id);
      if (txtvInfoMessage == null) {
        break missingId;
      }

      id = R.id.txtvLoadingMessage;
      TextView txtvLoadingMessage = ViewBindings.findChildViewById(rootView, id);
      if (txtvLoadingMessage == null) {
        break missingId;
      }

      return new InfoDialogBinding((ConstraintLayout) rootView, animationView, imgSuccess,
          loadingContainer, txtvInfoMessage, txtvLoadingMessage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}