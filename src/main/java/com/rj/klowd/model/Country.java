package com.rj.klowd.model;

import java.util.List;

public record Country(

    String iso2,
    String iso3,
    String country,
    List<String> cities){
}