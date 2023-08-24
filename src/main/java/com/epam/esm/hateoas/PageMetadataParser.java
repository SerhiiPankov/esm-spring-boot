package com.epam.esm.hateoas;

import com.epam.esm.lib.data.Page;
import org.springframework.hateoas.PagedModel;

public interface PageMetadataParser {
    PagedModel.PageMetadata getPageMetadata(Page<?> page);
}
