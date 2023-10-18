package fptu.swp391.shoppingcart.product.web;

import fptu.swp391.shoppingcart.product.dto.ProductDetailDto;
import fptu.swp391.shoppingcart.product.dto.ProductDto;
import fptu.swp391.shoppingcart.product.dto.ProductsResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

public interface ProjectController {
    // pagination get all product
    ResponseEntity<ProductsResponse> getAll(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "30") int limit);
    // pagination sort
    // pagination filter

    ResponseEntity<ProductsResponse> searchByKeyword(@RequestParam String keyword, // pagination search by keyword
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "30") int limit);
    // view product detail by id
    ResponseEntity<ProductDetailDto> findProductById(@PathVariable Long id);

    ResponseEntity<Resource> serveImage(@PathVariable String fileName) throws IOException;

    // combine search, filter, sort to one api,
    ResponseEntity<ProductsResponse> search(@RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String sort,
                                            @RequestParam(required = false) String category,
                                            @RequestParam(required = false) String size,
                                            @RequestParam(required = false) String colour,
                                            @RequestParam(defaultValue = "-1") int minPrice,
                                            @RequestParam(defaultValue = "-1") int maxPrice,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "30") int limit);
}
