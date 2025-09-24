package com.javanauta.usuario.controller;

import com.javanauta.usuario.business.UsuarioService;
import com.javanauta.usuario.business.ViaCepService;
import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.clients.ViaCepDTO;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import com.javanauta.usuario.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Cadastro e login de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ViaCepService viaCepService;

    @PostMapping
    @Operation(summary = "Save Users", description = "Creates a new user")
    @ApiResponse(responseCode = "200", description = "User successfully saved")
    @ApiResponse(responseCode = "409", description = "User already registered")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }
    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Authenticates the user")
    @ApiResponse(responseCode = "200", description = "User successfully logged in")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @ApiResponse(responseCode = "500", description = "Server error")
    public String login(@RequestBody UsuarioDTO usuarioDTO){
        Authentication authentiation = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
                        usuarioDTO.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentiation.getName());
    }

    @GetMapping
    @Operation(summary = "Fetch user data by email", description = "Retrieves user information")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "403", description = "User not registered")
    @ApiResponse(responseCode = "500", description = "Server error")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Delete user by email", description = "Removes a user from the system")
    @ApiResponse(responseCode = "200", description = "User successfully deleted")
    @ApiResponse(responseCode = "403", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Update user data", description = "Modifies user information")
    @ApiResponse(responseCode = "200", description = "User successfully updated")
    @ApiResponse(responseCode = "403", description = "User not registered")
    @ApiResponse(responseCode = "500", description = "Server error")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    public ResponseEntity<UsuarioDTO> atualizaDadoUsuario(@RequestBody UsuarioDTO dto,
                                                          @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }

    @PutMapping("/endereco")
    @Operation(summary = "Update user address", description = "Modifies the user's address")
    @ApiResponse(responseCode = "200", description = "Address successfully updated")
    @ApiResponse(responseCode = "403", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO dto,
                                                        @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
    }

    @PutMapping("/telefone")
    @Operation(summary = "Update user phone number", description = "Modifies the user's phone number")
    @ApiResponse(responseCode = "200", description = "Phone number successfully updated")
    @ApiResponse(responseCode = "403", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO dto,
                                                        @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }

    @PostMapping("/endereco")
    @Operation(summary = "Save user address", description = "Registers the user's address")
    @ApiResponse(responseCode = "200", description = "Address successfully saved")
    @ApiResponse(responseCode = "403", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestBody EnderecoDTO dto,
                                                        @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, dto));
    }

    @PostMapping("/telefone")
    @Operation(summary = "Save user phone number", description = "Registers the user's phone number")
    @ApiResponse(responseCode = "200", description = "Phone number successfully saved")
    @ApiResponse(responseCode = "403", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    public ResponseEntity<TelefoneDTO> cadastraTelefone(@RequestBody TelefoneDTO dto,
                                                        @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, dto));
    }
    @GetMapping("/endereco/{cep}")
    @Operation(summary = "retrieve data from postal code", description = "Retrieve data from postal code")
    @ApiResponse(responseCode = "200", description = "Postal Code founded")
    @ApiResponse(responseCode = "400", description = "Postal Code Invalid")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<ViaCepDTO> buscarDadosCep(@PathVariable("cep") String cep){
        return ResponseEntity.ok(viaCepService.buscaDadosEndereco(cep));
    }
}
