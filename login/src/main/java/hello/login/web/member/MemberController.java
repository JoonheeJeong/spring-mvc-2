package hello.login.web.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String getAddForm(@ModelAttribute("member") Member member) {
        return "members/add-member-form";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute Member member,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "members/add-member-form";
        }

        memberRepository.save(member);

        return "redirect:/";
    }

}
