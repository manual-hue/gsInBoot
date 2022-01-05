package org.team.gstreet.reqboard.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.team.gstreet.reqboard.entity.*;
import org.team.gstreet.saleboard.entity.QSaleBoard;
import org.team.gstreet.saleboard.entity.SaleBoard;

import java.util.List;
import java.util.stream.Collectors;


@Log4j2
public class ReqBoardSearchImpl extends QuerydslRepositorySupport implements ReqBoardSearch {

    public ReqBoardSearchImpl() {
        super(ReqBoard.class);
    }


    @Override
    public Page<Object[]> search(char[] typeArr, String keyword, Pageable pageable) { //검색처리
        log.info("search1");

        QReqBoard qReqBoard = QReqBoard.reqBoard; //ReqBoard 가져오기
        QReqReply qReqReply = QReqReply.reqReply; //댓글 entity 가져오기


        JPQLQuery<ReqBoard> query = from(qReqBoard);// jpql쿼리 만들어주기
        query.leftJoin(qReqReply).on(qReqReply.reqBoard.eq(qReqBoard));


        query.groupBy(qReqBoard); //group by 걸어주기

        if (typeArr != null && typeArr.length > 0) {
            BooleanBuilder builder = new BooleanBuilder();
            for (char type : typeArr) {
                if (type == 'T') {
                    builder.or(qReqBoard.r_title.contains(keyword));
                } else if (type == 'C') {
                    builder.or(qReqBoard.r_content.contains(keyword));
                } else if (type == 'W') {
                    builder.or(qReqBoard.r_writer.contains(keyword));
                } else if (type == 'R') {
                    builder.or(qReqBoard.r_category.contains(keyword));
                } else if (type == 'H') {
                    builder.or(qReqBoard.tags.contains(keyword));
                }
            }
            query.where(builder);
        }

        JPQLQuery<Tuple> selectQuery = query.select(qReqBoard.bno, qReqBoard.r_title, qReqBoard.r_writer, qReqBoard.r_regDate, qReqReply.count());

        this.getQuerydsl().applyPagination(pageable, selectQuery);

        List<Tuple> tupleList = selectQuery.fetch();
        long totalCount = selectQuery.fetchCount();

        List<Object[]> arr = tupleList.stream().map(tuple -> tuple.toArray()).
                collect(Collectors.toList());

        return new PageImpl<>(arr, pageable, totalCount);
    }

    @Override
    public Page<Object[]> searchWithAll(char[] typeArr, String keyword, Pageable pageable) {

        log.info("searchWithAll");

        QReqBoard qReqBoard = QReqBoard.reqBoard;
        QReqReply qReqReply = QReqReply.reqReply;


        //Board b left join Reply r on r.board.bno = b.bno

        JPQLQuery<ReqBoard> query = from(qReqBoard);
        query.leftJoin(qReqReply).on(qReqReply.reqBoard.eq(qReqBoard));
        query.groupBy(qReqBoard);

        if (typeArr != null && typeArr.length > 0) {
            BooleanBuilder builder = new BooleanBuilder();
            for (char type : typeArr) {
                if (type == 'T') {
                    builder.or(qReqBoard.r_title.contains(keyword));
                } else if (type == 'C') {
                    builder.or(qReqBoard.r_content.contains(keyword));
                } else if (type == 'W') {
                    builder.or(qReqBoard.r_writer.contains(keyword));
                } else if (type == 'R') {
                    builder.or(qReqBoard.r_category.contains(keyword));
                }
            }
            query.where(builder);
        }



        JPQLQuery<Tuple> selectQuery = query.select(qReqBoard.bno, qReqBoard.r_category, qReqBoard.r_title, qReqBoard.r_writer, qReqBoard.r_regDate, qReqBoard.r_modDate,qReqReply.count(), qReqBoard.views);

        this.getQuerydsl().applyPagination(pageable, selectQuery); //리스트에 페이징주기

        log.info(selectQuery);

        List<Tuple> tupleList = selectQuery.fetch();
        long totalCount = selectQuery.fetchCount(); //count는 무조건 long 값

        List<Object[]> arr = tupleList.stream().map(tuple -> tuple.toArray()).
                                                    collect(Collectors.toList());
        return new PageImpl<>(arr, pageable, totalCount);
    }

    @Override
    public Page<ReqBoard> searchMain(char[] typeArr, String keyword, Pageable pageable) { //메인시 검색처리
        log.info("search1");

        QReqBoard qReqBoard = QReqBoard.reqBoard;

        JPQLQuery<ReqBoard> query = from(qReqBoard);


        if (typeArr != null && typeArr.length > 0) {
            BooleanBuilder builder = new BooleanBuilder();
            for (char type : typeArr) {
                if (type == 'T') {
                    builder.or(qReqBoard.r_title.contains(keyword));
                } else if (type == 'C') {
                    builder.or(qReqBoard.r_content.contains(keyword));
                } else if (type == 'W') {
                    builder.or(qReqBoard.r_writer.contains(keyword));
                } else if (type == 'R') {
                    builder.or(qReqBoard.r_category.contains(keyword));
                }
            }
            query.where(builder);
        }
        query.where(qReqBoard.bno.gt(0L)); //bno > 0

        JPQLQuery<ReqBoard> pagingQuery = this.getQuerydsl().applyPagination(pageable, query);

        List<ReqBoard> reqBoards = pagingQuery.fetch();
        long totalCount = pagingQuery.fetchCount();


        return new PageImpl<>(reqBoards, pageable, totalCount);

    }

}
