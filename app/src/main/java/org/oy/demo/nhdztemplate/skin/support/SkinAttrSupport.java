package org.oy.demo.nhdztemplate.skin.support;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import org.oy.demo.nhdztemplate.skin.attr.SkinAttr;
import org.oy.demo.nhdztemplate.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * 皮肤属性解析类
 * Created by Mro on 2017/4/4.
 */
public class SkinAttrSupport {
    /**
     * 获取attr的属性
     */
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        // 解析 背景 src textcolor
        List<SkinAttr> list = new ArrayList<>();
        int attrLenght = attrs.getAttributeCount();
        for (int i = 0; i < attrLenght; i++) {
            // 获取名字和值
            String attrName = attrs.getAttributeName(i);
            String attrVaule = attrs.getAttributeValue(i);

            // 获取需要的值
            SkinType skinType = getSkinType(attrName);
            if (skinType != null) {
                String resourceName = getResourceName(context, attrVaule);
                if (TextUtils.isEmpty(resourceName)) {
                    continue;
                }
                SkinAttr skinAttr = new SkinAttr(resourceName, skinType);
                list.add(skinAttr);
            }

        }
        return list;
    }

    /**
     * 获取资源名称
     */
    private static String getResourceName(Context context, String attrVaule) {
        if (attrVaule.startsWith("@")) {
            attrVaule = attrVaule.substring(1);
            int attrId = Integer.parseInt(attrVaule);
            return context.getResources().getResourceEntryName(attrId);
        }
        return null;
    }

    /**
     * 通过属性名返回类型
     */
    private static SkinType getSkinType(String attrName) {
        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if (skinType.getResourceName().equals(attrName)) {
                return skinType;
            }
        }
        return null;
    }
}
