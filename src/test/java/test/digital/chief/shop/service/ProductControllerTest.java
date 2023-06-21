package test.digital.chief.shop.service;


import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.digital.chief.shop.ShopApplication;
import ru.digital.chief.shop.controller.ProductController;
import ru.digital.chief.shop.exception.CustomErrorCode;
import ru.digital.chief.shop.model.dto.ProductDto;
import ru.digital.chief.shop.model.dto.ShopDto;
import ru.digital.chief.shop.service.impl.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = {ShopApplication.class})
@Import(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService service;
    private List<ProductDto> products = new ArrayList<>();

    private final ObjectMapper writer = new ObjectMapper().disable(MapperFeature.USE_ANNOTATIONS);


    @BeforeEach
    public void setUp() {
        products = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            ProductDto productDto = ProductDto.builder()
                    .id(1L)
                    .name("name " + i)
                    .brand("brand " + i)
                    .quantity(Integer.toUnsignedLong(i))
                    .price(BigDecimal.valueOf(i))
                    .category("category" + i)
                    .shop(ShopDto.builder()
                            .id(Integer.toUnsignedLong(i + 1))
                            .name("shop name " + i)
                            .build())
                    .build();
            products.add(productDto);
        }
    }

    @Test
    void givenProductList_whenFindAll_thenReturn() throws Exception {
        Mockito.when(service.findPage(Mockito.anyInt(), Mockito.anyInt())).thenReturn(products);

        mockMvc.perform(get("/products?size=2&page=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(products.size())));


        Mockito.verify(service, Mockito.times(1))
                .findPage(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void givenValidProduct_whenCreate_thenReturnNew() throws Exception {
        ProductDto productDto = products.get(1);
        Mockito.when(service.create(Mockito.any())).thenReturn(productDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(productDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.brand").value(productDto.getBrand()))
                .andExpect(jsonPath("$.quantity").value(productDto.getQuantity()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()))
                .andExpect(jsonPath("$.category").value(productDto.getCategory()));


        Mockito.verify(service, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    void givenInvalidProduct_whenCreate_thenThrowEx() throws Exception {
        ProductDto productDto = products.get(1);
        productDto.setShop(null);
        productDto.setPrice(BigDecimal.valueOf(-1));
        productDto.setQuantity(-1L);
        productDto.setBrand("");
        productDto.setName("12");

        Mockito.when(service.create(Mockito.any())).thenReturn(productDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(productDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(CustomErrorCode.CONSTRAINT_VIOLATION.getCode()))
                .andExpect(jsonPath("$.message").isNotEmpty());

        Mockito.verify(service, Mockito.times(0)).create(Mockito.any());
    }

    @Test
    void givenProduct_whenFindById_thenReturn() throws Exception {
        ProductDto productDto = products.get(0);

        Mockito.when(service.findById(Mockito.anyLong())).thenReturn(productDto);

        mockMvc.perform(get("/products/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.brand").value(productDto.getBrand()))
                .andExpect(jsonPath("$.quantity").value(productDto.getQuantity()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()))
                .andExpect(jsonPath("$.category").value(productDto.getCategory()));

        Mockito.verify(service, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void givenProduct_whenDeleteById_thenNoContent() throws Exception {

        Mockito.doNothing().when(service).deleteById(1);

        mockMvc.perform(delete("/products/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        Mockito.verify(service, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void givenProduct_whenPatchById_thenPatchedProduct() throws Exception {
        ProductDto productDto = products.get(0);
        Mockito.when(service.update(Mockito.any())).thenReturn(productDto);

        mockMvc.perform(patch("/products/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(productDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.brand").value(productDto.getBrand()))
                .andExpect(jsonPath("$.quantity").value(productDto.getQuantity()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()))
                .andExpect(jsonPath("$.category").value(productDto.getCategory()));

        Mockito.verify(service, Mockito.times(1)).update(Mockito.any());
    }
}
