package com.rj.klowd.model;

import java.util.List;

public record CountryApiResponse (
    boolean error,
    String msg,
    List<Country> data
    ){
}
