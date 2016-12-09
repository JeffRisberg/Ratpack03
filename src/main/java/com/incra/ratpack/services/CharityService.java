package com.incra.ratpack.services;

import com.incra.ratpack.models.Charity;
import ratpack.exec.Promise;

import java.util.List;

/**
 * @author Jeff Risberg
 * @since 6/10/16
 */
public interface CharityService {

    public Promise<List<Charity>> list();

}
