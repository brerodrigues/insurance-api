package com.audsat.insurance.controller;

import com.audsat.insurance.dto.InsuranceDTO;
import com.audsat.insurance.service.InsuranceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InsuranceControllerTest {

    @Mock
    private InsuranceService insuranceService;

    @InjectMocks
    private InsuranceController insuranceController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(insuranceController).build();
    }

    @Test
    void shouldCreateInsurance() throws Exception {
        InsuranceDTO insuranceDTO = new InsuranceDTO();
        insuranceDTO.setId(1L);

        when(insuranceService.createInsurance(any(InsuranceDTO.class))).thenReturn(insuranceDTO);

        mockMvc.perform(post("/insurance/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(insuranceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(insuranceService, times(1)).createInsurance(any(InsuranceDTO.class));
    }

    @Test
    void shouldGetInsuranceById() throws Exception {
        InsuranceDTO insuranceDTO = new InsuranceDTO();
        insuranceDTO.setId(1L);

        when(insuranceService.getInsurance(1L)).thenReturn(Optional.of(insuranceDTO));

        mockMvc.perform(get("/insurance/budget/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(insuranceService, times(1)).getInsurance(1L);
    }

    @Test
    void shouldUpdateInsurance() throws Exception {
        InsuranceDTO insuranceDTO = new InsuranceDTO();
        insuranceDTO.setId(1L);

        when(insuranceService.updateInsurance(eq(1L), any(InsuranceDTO.class))).thenReturn(insuranceDTO);

        mockMvc.perform(put("/insurance/budget/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(insuranceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(insuranceService, times(1)).updateInsurance(eq(1L), any(InsuranceDTO.class));
    }

    @Test
    void shouldDeleteInsurance() throws Exception {
        mockMvc.perform(delete("/insurance/budget/{id}", 1))
                .andExpect(status().isOk());

        verify(insuranceService, times(1)).deleteInsurance(1L);
    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
