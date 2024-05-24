package lk.ijse.helloShoesManagementSystem.controller;

import lk.ijse.helloShoesManagementSystem.service.AdminPanelService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/adminPanel")
@RequiredArgsConstructor
public class AdminPanel {

    private final AdminPanelService adminPanelService;
    private static final Logger logger = LoggerFactory.getLogger(AdminPanel.class);

    @GetMapping("/health")
    public String adminPanelHealth(){
        return "OK";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPanelData(){
        logger.info("Received request for get admin panel details");
        try {
            return ResponseEntity.ok(adminPanelService.getPanelData());
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
