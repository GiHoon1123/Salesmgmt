package io.dustin.finstage.user.application.service;

import io.dustin.finstage.common.annotation.UseCase;
import io.dustin.finstage.common.exception.custom.InvalidDepartmentException;
import io.dustin.finstage.user.application.dto.MsGraphUserInfo;
import io.dustin.finstage.user.application.port.in.GetUsersInfoUseCase;
import io.dustin.finstage.user.application.port.in.query.GetUsersInfoQuery;
import io.dustin.finstage.user.application.port.out.LoadUsersInfoPort;
import io.dustin.finstage.user.domain.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetUsersInfoService implements GetUsersInfoUseCase {

    private final LoadUsersInfoPort loadUserInfoPort;
    @Override
    public List<User> getUsersInfo(GetUsersInfoQuery query) {
        List<String> departmentsName = query.getDepartmentList();
        List<MsGraphUserInfo> getUsersInfo = loadUserInfoPort.getUsersInfoFromMsGraph(departmentsName);

        if (getUsersInfo == null || getUsersInfo.isEmpty()){
            throw new InvalidDepartmentException("올바른 부서 정보를 입력해야 합니다.");
        }


        return getUsersInfo.stream()
                .flatMap(department -> department.members().stream()
                        .map(member -> User.of(
                                member.displayName(),
                                member.mail(),
                                department.name()
                        ))
                )
                .toList();
    }
}
