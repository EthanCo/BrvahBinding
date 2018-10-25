package com.lib.frame.view.encapsulation.brvah.binding;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;

import com.heiko.brvahbinding.R;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * RecyclerView 分割线 管理类
 *
 * @author EthanCo
 * @since 2017/11/15
 */

public class BrvahDividerManagers {
    public interface DividerFactory {
        RecyclerView.ItemDecoration create(RecyclerView recyclerView);
    }

    public static DividerFactory def() {
        @ColorRes final int dividerColorRes = R.color.divider;
        final int strokeWidth = 1;

        return new DividerFactory() {
            @Override
            public RecyclerView.ItemDecoration create(RecyclerView recyclerView) {
                Context context = recyclerView.getContext();
                @ColorInt final int dividerColor = ResourcesCompat.getColor(
                        context.getResources(), dividerColorRes, null);


                recyclerView.addItemDecoration(
                        new HorizontalDividerItemDecoration.Builder(context)
                                .color(dividerColor)
                                .size(strokeWidth)
                                .build());
                return null;
                /*float strokeWidthDp = calcStokeWidthDp(context, strokeWidth);
                final Y_Divider dividerCommon = new Y_DividerBuilder()
                        .setBottomSideLine(true, dividerColor, strokeWidthDp, 0, 0)
                        .create();

                return new Y_DividerItemDecoration(context) {
                    @Override
                    public Y_Divider getDivider(int itemPosition) {
                        return dividerCommon;
                    }
                };*/
            }
        };
    }

    public static DividerFactory liner(@ColorRes final int dividerColorRes, final int strokeWidth) {
        return new DividerFactory() {
            @Override
            public RecyclerView.ItemDecoration create(RecyclerView recyclerView) {
                Context context = recyclerView.getContext();
                @ColorInt final int dividerColor = ResourcesCompat.getColor(
                        context.getResources(), dividerColorRes, null);

                float strokeWidthDp = calcStokeWidthDp(context, strokeWidth);
                final Y_Divider dividerCommon = new Y_DividerBuilder()
                        .setBottomSideLine(true, dividerColor, strokeWidthDp, 0, 0)
                        .create();

                return new Y_DividerItemDecoration(context) {
                    @Override
                    public Y_Divider getDivider(int itemPosition) {
                        return dividerCommon;
                    }
                };
            }
        };
    }

    /**
     *
     * @param dividerColorRes 比如 R.color.divider
     * @param strokeWidth 单位:dp
     * @param spanCount 每行item的个数
     * @return
     */
    public static DividerFactory grid(@ColorRes final int dividerColorRes, final float strokeWidth, final int spanCount) {
        return new DividerFactory() {
            @Override
            public RecyclerView.ItemDecoration create(RecyclerView recyclerView) {
                Context context = recyclerView.getContext();
                @ColorInt final int dividerColor = ResourcesCompat.getColor(
                        context.getResources(), dividerColorRes, null);

                float strokeWidthDp = calcStokeWidthDp(context, strokeWidth);
                final Y_Divider dividerCommon = new Y_DividerBuilder()
                        .setRightSideLine(true, dividerColor, strokeWidthDp, 0, 0)
                        .setBottomSideLine(true, dividerColor, strokeWidthDp, 0, 0)
                        .create();

                final Y_Divider dividerEnd = new Y_DividerBuilder()
                        .setBottomSideLine(true, dividerColor, strokeWidthDp, 0, 0)
                        .create();

                return new Y_DividerItemDecoration(context) {
                    @Override
                    public Y_Divider getDivider(int itemPosition) {
                        Y_Divider divider;
                        int remainder = itemPosition % spanCount;
                        if (remainder == spanCount - 1) {
                            divider = dividerEnd;
                        } else {
                            divider = dividerCommon;
                        }
                        return divider;
                    }
                };
            }
        };
    }

    private static float calcStokeWidthDp(Context context, double strokeWidth) {
        float stokeWidthDp;
        if (strokeWidth == 0) {
            stokeWidthDp = BrvahBindingUtil.px2dp(context, 1);
        } else if (strokeWidth < 0) {
            float px = (float) Math.abs(strokeWidth);
            stokeWidthDp = BrvahBindingUtil.px2dp(context,px);
        } else {
            stokeWidthDp = (float) strokeWidth;
        }
        return stokeWidthDp;
    }
}
