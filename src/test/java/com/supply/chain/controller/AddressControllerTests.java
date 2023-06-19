package com.supply.chain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supply.chain.dto.AddressDto;
import com.supply.chain.mapper.AddressMapper;
import com.supply.chain.model.Address;
import com.supply.chain.model.Order;
import com.supply.chain.model.Role;
import com.supply.chain.security.SecurityRole;
import com.supply.chain.service.impl.AddressServiceImpl;
import com.supply.chain.util.AddressFactory;
import com.supply.chain.util.OrderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class AddressControllerTests {

    private MockMvc mvc;

    @Mock
    private AddressServiceImpl addressService;

    @InjectMocks
    private AddressController addressController;

    private ObjectMapper objectMapper;

    private Address address;

    private AddressDto addressDto;

    private Order order;


    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(addressController)
                .build();

        order = OrderFactory.getBaseOrder();
        address = AddressFactory.getBaseAddress();
        addressDto = AddressMapper.mapToAddressDto(address);
        objectMapper = new ObjectMapper();
    }

    @DisplayName("getAllAddresses endpoint")
    @Test
    @WithMockUser(username = "bill")
    public void givenAddressList_WhenGetAllAddresses_thenReturnAddressList() throws Exception {
        AddressDto secondAddressDto = new AddressDto(null,"Main","New York","111111","USA","Horia","Dascalu");
        SecurityRole grantedAuthority = new SecurityRole(new Role("ROLE_USER"));
        Collection<SecurityRole> col = List.of(grantedAuthority);
        given(addressService.getAllAddresses(col,"bill")).willReturn(Arrays.asList(addressDto,secondAddressDto));

        ResultActions response = mvc.perform(
                get("/addresses"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("saveAddress endpoint")
    @Test
    public void givenAddressObject_whenSaveObject_thenReturnSavedObject() throws Exception {

        given(addressService.saveAddress(addressDto)).willReturn(addressDto);

        MvcResult result = this.mvc.perform(post("/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Address savedAddress = objectMapper.readValue(json, Address.class);

        assertThat(savedAddress.getStreet()).isEqualTo(addressDto.getStreet());
        assertThat(savedAddress.getCity()).isEqualTo(addressDto.getCity());
    }

    @DisplayName("updateAddress endpoint")
    @Test
    public void givenOrderId_whenUpdateAddress_thenReturnUpdatedAddress() throws Exception {
        AddressDto dtoToEdit = new AddressDto(null,"Garii",null,null,null,null,null);
        Address editedAddress = new Address("Garii", "City", "000000", "Country","Horia","Dascalu");

        given(addressService.updateAddress(order.getOrderId(),dtoToEdit)).willReturn(AddressMapper.mapToAddressDto(editedAddress));

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.put("/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoToEdit)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        String json = result.getResponse().getContentAsString();
        Address savedAddress = objectMapper.readValue(json, Address.class);

        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getStreet()).isEqualTo(dtoToEdit.getStreet());
    }

}
