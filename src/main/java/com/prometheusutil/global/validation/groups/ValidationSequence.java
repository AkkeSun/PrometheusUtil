package com.prometheusutil.global.validation.groups;


import com.prometheusutil.global.validation.groups.ValidationGroups.CustomGroups;
import com.prometheusutil.global.validation.groups.ValidationGroups.NotBlankGroups;
import com.prometheusutil.global.validation.groups.ValidationGroups.SizeGroups;
import jakarta.validation.GroupSequence;

@GroupSequence({NotBlankGroups.class, SizeGroups.class, CustomGroups.class})
public interface ValidationSequence {

}
