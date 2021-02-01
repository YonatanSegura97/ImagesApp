package com.segura.imagesapp.domain.useCase

import com.google.common.truth.Truth.assertThat
import com.segura.imagesapp.data.dataSource.ImagesDao
import com.segura.imagesapp.data.dataSource.ImagesRemoteDataSource
import com.segura.imagesapp.data.repository.ImageRepositoryImpl
import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.domain.model.Resource
import com.segura.imagesapp.domain.model.Result
import com.segura.imagesapp.domain.model.User
import com.segura.imagesapp.utils.CoroutineTestRule
import com.segura.imagesapp.utils.userDetailTest
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class GetUserDetailUseCaseTest {


    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private val imagesRepository = mock(ImagesRepository::class.java)

    private val getUserDetailUseCaseTest = GetUserDetailUseCase(imagesRepository)
    private val userName = "Yonatan"

    @Test
    fun `GIVEN valid data WHEN get user THEN return a valid user details`() {
        coroutineTestRule.testDispatcher.runBlockingTest {

            //GIVEN
            val flow = flow {
                emit(Resource.success(userDetailTest))
            }
            given(imagesRepository.getUserDetail(ArgumentMatchers.anyString())).willReturn(flow)

            //WHEN
            val firstElement = getUserDetailUseCaseTest.execute(userName).first()

            //THEN
            assertThat(firstElement).isEqualTo(Resource.success(userDetailTest))

        }
    }

}