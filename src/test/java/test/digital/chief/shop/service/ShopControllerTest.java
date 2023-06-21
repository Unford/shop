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
import ru.digital.chief.shop.controller.ShopController;
import ru.digital.chief.shop.exception.CustomErrorCode;
import ru.digital.chief.shop.model.dto.ProductDto;
import ru.digital.chief.shop.model.dto.ShopDto;
import ru.digital.chief.shop.service.impl.ShopService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopController.class)
@ContextConfiguration(classes = {ShopApplication.class})
@Import(ShopController.class)
class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShopService service;
    private List<ShopDto> shops = new ArrayList<>();

    private final ObjectMapper writer = new ObjectMapper().disable(MapperFeature.USE_ANNOTATIONS);


    @BeforeEach
    public void setUp() {
        shops = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            List<ProductDto> productDtos = new ArrayList<>();
            for (int j = i; j > 0; j--) {
                ProductDto productDto = ProductDto.builder()
                        .id(1L)
                        .name("name " + i)
                        .brand("brand " + i)
                        .quantity(Integer.toUnsignedLong(i))
                        .price(BigDecimal.valueOf(i))
                        .category("category" + i).build();
                productDtos.add(productDto);
            }
            shops.add(ShopDto.builder()
                    .name("name " + i)
                    .category("category" + i)
                    .workingHours("01-1" + i)
                    .phone("123123124" + i)
                    .products(productDtos)
                    .build());
        }

    }


    @Test
    void givenShopsList_whenFindAll_thenReturn() throws Exception {
        Mockito.when(service.findPage(Mockito.anyInt(), Mockito.anyInt())).thenReturn(shops);

        mockMvc.perform(get("/shops?size=2&page=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(shops.size())));


        Mockito.verify(service, Mockito.times(1))
                .findPage(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void givenValidShop_whenCreate_thenReturnNew() throws Exception {
        ShopDto shopDto = shops.get(1);
        Mockito.when(service.create(Mockito.any())).thenReturn(shopDto);

        mockMvc.perform(post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(shopDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(shopDto.getName()))
                .andExpect(jsonPath("$.phone").value(shopDto.getPhone()))
                .andExpect(jsonPath("$.workingHours").value(shopDto.getWorkingHours()))
                .andExpect(jsonPath("$.category").value(shopDto.getCategory()))
                .andExpect(jsonPath("$.products", hasSize(shopDto.getProducts().size())));


        Mockito.verify(service, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    void givenInvalidShop_whenCreate_thenThrowEx() throws Exception {
        ShopDto shopDto = shops.get(1);
        shopDto.setName("12");
        shopDto.setWorkingHours("12:11");
        shopDto.setPhone("Phone");

        Mockito.when(service.create(Mockito.any())).thenReturn(shopDto);

        mockMvc.perform(post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(shopDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(CustomErrorCode.CONSTRAINT_VIOLATION.getCode()))
                .andExpect(jsonPath("$.message").isNotEmpty());

        Mockito.verify(service, Mockito.times(0)).create(Mockito.any());
    }

    @Test
    void givenShop_whenFindById_thenReturn() throws Exception {
        ShopDto shopDto = shops.get(0);

        Mockito.when(service.findById(Mockito.anyLong())).thenReturn(shopDto);

        mockMvc.perform(get("/shops/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(shopDto.getName()))
                .andExpect(jsonPath("$.phone").value(shopDto.getPhone()))
                .andExpect(jsonPath("$.workingHours").value(shopDto.getWorkingHours()))
                .andExpect(jsonPath("$.category").value(shopDto.getCategory()))
                .andExpect(jsonPath("$.products", hasSize(shopDto.getProducts().size())));

        Mockito.verify(service, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void givenShop_whenDeleteById_thenNoContent() throws Exception {

        Mockito.doNothing().when(service).deleteById(1);

        mockMvc.perform(delete("/shops/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        Mockito.verify(service, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void givenShop_whenPatchById_thenPatchedProduct() throws Exception {
        ShopDto shopDto = shops.get(0);
        Mockito.when(service.update(Mockito.any())).thenReturn(shopDto);

        mockMvc.perform(patch("/shops/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(shopDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(shopDto.getName()))
                .andExpect(jsonPath("$.phone").value(shopDto.getPhone()))
                .andExpect(jsonPath("$.workingHours").value(shopDto.getWorkingHours()))
                .andExpect(jsonPath("$.category").value(shopDto.getCategory()))
                .andExpect(jsonPath("$.products", hasSize(shopDto.getProducts().size())));

        Mockito.verify(service, Mockito.times(1)).update(Mockito.any());
    }
}
