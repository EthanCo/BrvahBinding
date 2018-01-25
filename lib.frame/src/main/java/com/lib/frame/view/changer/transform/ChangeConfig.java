package com.lib.frame.view.changer.transform;

/**
 * Fragment改变参数
 *
 * @author EthanCo
 * @since 2017/10/25
 */

public class ChangeConfig {
    public static final ChangeConfig BACK = new ChangeConfig(true);
    public static final ChangeConfig BACK_CACHE = new ChangeConfig(true, true);
    public static final ChangeConfig BACK_CACHE_ANIM = new ChangeConfig(true, true, true);

    private String fragmentTag;
    private boolean isBackStack;
    private boolean useCache;
    private boolean useAnimation;

    public ChangeConfig() {
    }

    public ChangeConfig(boolean isBackStack) {
        this.isBackStack = isBackStack;
    }

    public ChangeConfig(boolean isBackStack, boolean useCache) {
        this.isBackStack = isBackStack;
        this.useCache = useCache;
    }

    public ChangeConfig(boolean isBackStack, boolean useCache, boolean useAnimation) {
        this.isBackStack = isBackStack;
        this.useCache = useCache;
        this.useAnimation = useAnimation;
    }

    public ChangeConfig(boolean isBackStack, boolean useCache, boolean useAnimation, String fragmentTag) {
        this.fragmentTag = fragmentTag;
        this.isBackStack = isBackStack;
        this.useCache = useCache;
        this.useAnimation = useAnimation;
    }

    public String getFragmentTag() {
        return fragmentTag;
    }

    public void setFragmentTag(String fragmentTag) {
        this.fragmentTag = fragmentTag;
    }

    public boolean isBackStack() {
        return isBackStack;
    }

    public void setBackStack(boolean backStack) {
        isBackStack = backStack;
    }

    public boolean isUseCache() {
        return useCache;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public boolean isUseAnimation() {
        return useAnimation;
    }

    public void setUseAnimation(boolean useAnimation) {
        this.useAnimation = useAnimation;
    }
}
