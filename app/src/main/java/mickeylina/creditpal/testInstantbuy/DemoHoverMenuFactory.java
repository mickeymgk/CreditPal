/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mickeylina.creditpal.testInstantbuy;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import io.mattcarroll.hover.Content;
import io.mattcarroll.hover.content.menus.DoNothingMenuAction;
import io.mattcarroll.hover.content.menus.Menu;
import io.mattcarroll.hover.content.menus.MenuItem;
import io.mattcarroll.hover.content.menus.MenuListContent;
import io.mattcarroll.hover.content.menus.ShowSubmenuMenuAction;
import io.mattcarroll.hover.content.toolbar.ToolbarNavigator;

/**
 * Can create a Hover menu from code or from file.
 */
class DemoHoverMenuFactory {

    /**
     * Example of how to create a menu in code.
     * @return HoverMenu
     */
    public DemoHoverMenuTest createDemoMenuFromCode(@NonNull Context context) {
        Menu drillDownMenuLevelTwo = new Menu("Demo Menu - Level 2", Arrays.asList(
                new MenuItem(UUID.randomUUID().toString(), "Google", new DoNothingMenuAction()),
                new MenuItem(UUID.randomUUID().toString(), "Amazon", new DoNothingMenuAction())
        ));
        ShowSubmenuMenuAction showLevelTwoMenuAction = new ShowSubmenuMenuAction(drillDownMenuLevelTwo);

        Menu drillDownMenu = new Menu("Demo Menu", Arrays.asList(
                new MenuItem(UUID.randomUUID().toString(),"GPS", new DoNothingMenuAction()),
                new MenuItem(UUID.randomUUID().toString(),"Cell Tower Triangulation", new DoNothingMenuAction()),
                new MenuItem(UUID.randomUUID().toString(),"Location Services", showLevelTwoMenuAction)
        ));
        MenuListContent drillDownMenuNavigatorContent = new MenuListContent(context, drillDownMenu);
        st();
        ToolbarNavigator toolbarNavigator = new ToolbarNavigator(context);
        toolbarNavigator.pushContent(drillDownMenuNavigatorContent);
        toolbarNavigator.setBackgroundColor(context.getResources().getColor(android.R.color.white));

        Map<String, Content> demoMenuTest = new LinkedHashMap<>();
        demoMenuTest.put(DemoHoverMenuTest.INTRO_ID, new NonFullscreenContent(context.getApplicationContext()));
//        demoMenuTest.put(DemoHoverMenuTest.SELECT_COLOR_ID, toolbarNavigator);

        return new DemoHoverMenuTest(context, "CP",demoMenuTest);
    }

    private void st() {
    }

}
