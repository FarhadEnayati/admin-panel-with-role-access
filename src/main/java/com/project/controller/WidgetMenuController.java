package com.project.controller;

import com.project.core.exception.ApplicationExceptionsType;
import com.project.model.WidgetMenu;
import com.project.service.WidgetMenuService;
import com.project.utility.dozer.RequestView;
import com.project.utility.dozer.ResponseView;
import com.project.view.WidgetMenuView2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/menu")
@PreAuthorize("hasRole('ROLE_MANAGEMENT/MENU')")
public class WidgetMenuController extends BaseController {

    @Autowired
    private WidgetMenuService widgetMenuService;

    @ResponseView(WidgetMenuView2.class)
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<WidgetMenu> list() {
        return widgetMenuService.getAll();
    }

    @ResponseView(WidgetMenuView2.class)
    @RequestMapping(value = "list/parent", method = RequestMethod.GET)
    public List<WidgetMenu> listParent() {
        Short type = Short.parseShort("1");
        return widgetMenuService.getAll().stream().filter(e -> e.getMenuType().equals(type)).collect(Collectors.toList());
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequestView(WidgetMenuView2.class)
    public Integer save(@RequestBody WidgetMenu widgetMenu) {
        return widgetMenuService.save(widgetMenu);
    }

    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    @RequestView(WidgetMenuView2.class)
    public void edit(@RequestBody WidgetMenu widgetMenu) {
        widgetMenuService.update(widgetMenu);
        setException("edit.successful", ApplicationExceptionsType.SUCCESS);
    }

    @ResponseView(WidgetMenuView2.class)
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public WidgetMenu getUser(@PathVariable Integer id) {
        return widgetMenuService.get(id);
    }

}
