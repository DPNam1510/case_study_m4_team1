package com.example.case_study_m4_team1.service.user;

import com.example.case_study_m4_team1.dto.user.PaymentRequestDTO;
import com.example.case_study_m4_team1.entity.*;
import com.example.case_study_m4_team1.enums.ClassStatus;
import com.example.case_study_m4_team1.enums.PaymentStatus;
import com.example.case_study_m4_team1.enums.RegisterStatus;
import com.example.case_study_m4_team1.repository.teacher.*;
import com.example.case_study_m4_team1.repository.user.IPayRepository;
import com.example.case_study_m4_team1.repository.user.IPaymentRegisterRepository;
import com.example.case_study_m4_team1.repository.user.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserStudyService implements IUserStudyService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IStudyScheduleRepository studyScheduleRepository;

    @Autowired
    private IClassRegisterRepository classRegisterRepository;

    @Autowired
    private ITeacherNoticeRepository teacherNoticeRepository;

    @Autowired
    private ITeacherReviewRepository teacherReviewRepository;

    @Autowired
    private IPayRepository payRepository;

    @Autowired
    private IPaymentRegisterRepository paymentRegisterRepository;

    @Override
    public List<StudySchedule> getOpenClasses() {
        // Lấy các lớp có status OPEN hoặc NOT_OPEN nhưng chưa đủ 5 học viên
        return studyScheduleRepository.findAll();
    }

    @Override
    public StudySchedule findScheduleById(int scheduleId) {
        return studyScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    @Override
    public int countRegisteredStudents(int scheduleId) {
        return classRegisterRepository.countByStudyScheduleIdAndStatusRegister(
                scheduleId, RegisterStatus.APPROVED);
    }

    @Override
    public Long registerClass(String username, int scheduleId) throws Exception {
        // Tìm user
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Users user = usersRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tìm lớp
        StudySchedule schedule = studyScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        // Kiểm tra đã đăng ký chưa
        boolean alreadyRegistered = classRegisterRepository
                .existsByUserIdAndStudyScheduleId(user.getId(), scheduleId);
        if (alreadyRegistered) {
            throw new Exception("Bạn đã đăng ký lớp này rồi!");
        }

        // Kiểm tra số lượng hiện tại
        int currentRegisters = classRegisterRepository
                .countByStudyScheduleIdAndStatusRegister(scheduleId, RegisterStatus.APPROVED);

        if (currentRegisters >= schedule.getMaxStudents()) {
            throw new Exception("Lớp đã đủ 10 học viên!");
        }

        // Tạo đăng ký mới
        ClassRegister classRegister = new ClassRegister();
        classRegister.setUser(user);
        classRegister.setStudySchedule(schedule);
        classRegister.setDateRegister(LocalDateTime.now());

        // Nếu chưa đủ 5 người thì status PENDING, nếu đủ 5 thì tự động APPROVED
        if (currentRegisters + 1 >= schedule.getMinStudents()) {
            classRegister.setStatusRegister(RegisterStatus.APPROVED);

            // Cập nhật status_class thành OPEN nếu đang NOT_OPEN
            if (schedule.getStatusClass() == ClassStatus.NOT_OPEN) {
                schedule.setStatusClass(ClassStatus.OPEN);
                studyScheduleRepository.save(schedule);
            }

            // Nếu đủ 10 người thì đóng lớp
            if (currentRegisters + 1 >= schedule.getMaxStudents()) {
                schedule.setStatusClass(ClassStatus.FULL);
                studyScheduleRepository.save(schedule);
            }
        } else {
            classRegister.setStatusRegister(RegisterStatus.PENDING);
        }

        ClassRegister saved = classRegisterRepository.save(classRegister);
        return saved.getId();
    }

    @Override
    public List<ClassRegister> getUserClasses(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Users user = usersRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return classRegisterRepository.findByUserId(user.getId());
    }

    @Override
    public List<TeacherNotice> getUserNotices(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Users user = usersRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return teacherNoticeRepository.findByClassRegister_UserId(user.getId());
    }

    @Override
    public List<TeacherReview> getUserReviews(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Users user = usersRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ClassRegister> registers = classRegisterRepository.findByUserId(user.getId());
        return registers.stream()
                .map(r -> teacherReviewRepository.findByClassRegisterId(r.getId()).orElse(null))
                .filter(r -> r != null)
                .toList();
    }

    @Override
    public ClassRegister findClassRegisterById(long id) {
        return classRegisterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class register not found"));
    }

    @Override
    public boolean isOwner(String username, long registerId) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Users user = usersRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ClassRegister register = findClassRegisterById(registerId);
        return register.getUser().getId() == user.getId();
    }

    @Override
    public void processPayment(PaymentRequestDTO paymentRequest, String username) throws Exception {
        // Kiểm tra quyền
        if (!isOwner(username, paymentRequest.getRegisterId())) {
            throw new Exception("Bạn không có quyền thanh toán cho đăng ký này!");
        }

        ClassRegister register = findClassRegisterById(paymentRequest.getRegisterId());

        // Kiểm tra đã thanh toán chưa
        if (paymentRegisterRepository.existsByClassRegister_Id(register.getId())) {
            PaymentRegister existing = paymentRegisterRepository.findByClassRegister_Id(register.getId()).get();
            if (existing.getStatus() == PaymentStatus.PAID) {
                throw new Exception("Đăng ký này đã được thanh toán rồi!");
            }
        }

        // Tìm hình thức thanh toán
        Pay pay = payRepository.findById(paymentRequest.getPayTypeId())
                .orElseThrow(() -> new Exception("Hình thức thanh toán không hợp lệ"));

        // Tạo payment mới hoặc cập nhật payment cũ
        PaymentRegister payment;
        if (paymentRegisterRepository.existsByClassRegister_Id(register.getId())) {
            payment = paymentRegisterRepository.findByClassRegister_Id(register.getId()).get();
        } else {
            payment = new PaymentRegister();
            payment.setClassRegister(register);
        }

        payment.setPay(pay);
        payment.setStatus(PaymentStatus.PAID); // Giả lập thanh toán thành công

        paymentRegisterRepository.save(payment);
    }
}
