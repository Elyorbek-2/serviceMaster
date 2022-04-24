package atm.appatmservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import atm.appatmservice.entity.Card;
import atm.appatmservice.entity.enums.RoleName;
import atm.appatmservice.payload.ApiResponse;
import atm.appatmservice.payload.CardDto;
import atm.appatmservice.repository.*;

import static atm.appatmservice.utils.CommonUtils.checkAuthority;

@Service
public class CardService {


    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardTypeRepository cardTypeRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse addCard(CardDto cardDto) {
        if (checkAuthority(RoleName.ROLE_STAFF)) {
            return new ApiResponse("You don't have the authority", false);
        }

        boolean existsByNumber = cardRepository.existsByNumber(cardDto.getNumber());
        if (existsByNumber) {
            return new ApiResponse("card with such a number already exists", false);
        }

        Card card = new Card();
        card.setNumber(cardDto.getNumber());
        card.setCvvCode(cardDto.getCvvCode());
        card.setFullName(cardDto.getFullName());
        card.setValidityPeriod(cardDto.getValidityPeriod());
        card.setCode(passwordEncoder.encode(cardDto.getCode()));
        card.setCardType(cardTypeRepository.findById(cardDto.getCardTypeId()).get());
        card.setBank(bankRepository.findById(cardDto.getBankId()).get());
        card.setCurrency(currencyRepository.findById(cardDto.getCurrencyId()).get());
        card.setStatus(cardDto.isStatus());
        card.setRole(roleRepository.findById(cardDto.getRoleId()).get());

        Card savedCard = cardRepository.save(card);

        return new ApiResponse("Card saved", true, savedCard);

    }

}
