/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.react.animated;

import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

/**
 * Animated node which takes two or more value node as an input and outputs a product of their
 * values
 */
/*package*/ class MultiplicationAnimatedNode extends ValueAnimatedNode {

  private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
  private final int[] mInputNodes;

  public MultiplicationAnimatedNode(
      ReadableMap config,
      NativeAnimatedNodesManager nativeAnimatedNodesManager) {
    mNativeAnimatedNodesManager = nativeAnimatedNodesManager;
    ReadableArray inputNodes = config.getArray("input");
    mInputNodes = new int[inputNodes.size()];
    for (int i = 0; i < mInputNodes.length; i++) {
      mInputNodes[i] = inputNodes.getInt(i);
    }
  }

  @Override
  public void update() {
    mValue = 1;
    for (int i = 0; i < mInputNodes.length; i++) {
      AnimatedNode animatedNode = mNativeAnimatedNodesManager.getNodeById(mInputNodes[i]);
      if (animatedNode != null && animatedNode instanceof ValueAnimatedNode) {
        mValue *= ((ValueAnimatedNode) animatedNode).getValue();
      } else {
        throw new JSApplicationCausedNativeException("Illegal node ID set as an input for " +
          "Animated.multiply node");
      }
    }
  }
}
