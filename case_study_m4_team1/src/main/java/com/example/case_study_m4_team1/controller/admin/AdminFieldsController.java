package com.example.case_study_m4_team1.controller.admin;

import com.example.case_study_m4_team1.entity.FieldBook;
import com.example.case_study_m4_team1.entity.Fields;
import com.example.case_study_m4_team1.enums.FieldStatus;
import com.example.case_study_m4_team1.service.admin.field.IFieldsServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("admin/fields")
public class AdminFieldsController {
    @Autowired
    private IFieldsServiceAdmin fieldsServiceAdmin;

    @GetMapping
    public String show(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "searchName", defaultValue = "") String searchName,
                       @RequestParam(value = "searchPrice", defaultValue = "0") double price){
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page,3,sort);
        Page<Fields> fieldsPage;
        if (price!=0){
            fieldsPage = fieldsServiceAdmin.search(searchName,price,pageable);
        }else {
            fieldsPage = fieldsServiceAdmin.findByNameContaining(searchName,pageable);
        }
        model.addAttribute("fieldsPage",fieldsPage);
        model.addAttribute("searchName",searchName);
        model.addAttribute("searchPrice",price);
        return "admin/field/list";
    }

    @GetMapping("{id}/detail")
    public String detail(@PathVariable int id,
                         Model model){
        model.addAttribute("field",fieldsServiceAdmin.findById(id));
        return "admin/field/detail";
    }


    @PostMapping("/update")
    public String updateFieldStatus(@ModelAttribute Fields field,
                                    RedirectAttributes redirectAttributes) {
        fieldsServiceAdmin.setFieldsMaintenance(field.getId(), field.getStatus());
        redirectAttributes.addFlashAttribute("mess","Cập nhật thành công !!");
        return "redirect:/admin/fields";
    }
}
