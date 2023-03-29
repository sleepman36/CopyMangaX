// Generated by view binder compiler. Do not edit!
package com.crow.module_discover.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.crow.module_discover.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DiscoverFragmentNovelBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView discoverTextview;

  private DiscoverFragmentNovelBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView discoverTextview) {
    this.rootView = rootView;
    this.discoverTextview = discoverTextview;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DiscoverFragmentNovelBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DiscoverFragmentNovelBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.discover_fragment_novel, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DiscoverFragmentNovelBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.discoverTextview;
      TextView discoverTextview = ViewBindings.findChildViewById(rootView, id);
      if (discoverTextview == null) {
        break missingId;
      }

      return new DiscoverFragmentNovelBinding((ConstraintLayout) rootView, discoverTextview);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
