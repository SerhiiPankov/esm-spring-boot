package com.epam.esm.hateoas;

import com.epam.esm.lib.data.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

@Component
public class PageMetadataParserImpl implements PageMetadataParser {
    @Override
    public PagedModel.PageMetadata getPageMetadata(Page<?> page) {
        return new PagedModel.PageMetadata(
                page.getPage().size(),
                page.getNumberPage(),
                page.getTotalRecords(),
                (long) Math.ceil((double) page.getTotalRecords() / page.getNumberRecordsPerPage()));
    }
}
